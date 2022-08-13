package com.bus.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bus.mapper.BusLineInfoMapper;
import com.bus.mapper.BusLineMsgInfoMapper;
import com.bus.mapper.StationInfoMapper;
import com.bus.pojo.BusLineInfo;
import com.bus.pojo.BusLineInfoExample;
import com.bus.pojo.BusLineMsgInfo;
import com.bus.pojo.BusLineMsgInfoExample;
import com.bus.pojo.StationInfo;
@Service
public class BusLineCalServiceImpl {

	
	@Autowired
	StationInfoMapper stationInfoMapper;
	@Autowired
	BusLineInfoMapper busLineInfoMapper;
	@Autowired
	BusLineMsgInfoMapper busLineMsgInfoMapper;
	/**
	 * 计算所有可达路径
	 * @param startId
	 * @param endId
	 * @return
	 */
	public List<Map<String,Object>> getBusLine(Integer startId,Integer endId){
		List<Map<String,Object>> rs = new ArrayList<Map<String,Object>>();
		if(startId==null||endId==null){
			return rs;
		}
		if(startId.equals(endId)){
			return rs;
		}
		BusLineInfoExample be = new BusLineInfoExample();
		List<BusLineInfo> bl = busLineInfoMapper.selectByExample(be);
		List<Map<String,Object>> lines = new ArrayList<Map<String,Object>>();//所有的线路
		List<Integer> lineIds = new ArrayList<Integer>();
		for(BusLineInfo b:bl){
			Map<String,Object> map = new HashMap<String,Object>();
			BusLineMsgInfoExample be2 = new BusLineMsgInfoExample();
			BusLineMsgInfoExample.Criteria bc2= be2.createCriteria();
			bc2.andLineIdEqualTo(b.getId());
			be2.setOrderByClause("no asc");//根据编号排序
			List<BusLineMsgInfo> bl2 = busLineMsgInfoMapper.selectByExample(be2);
			map.put("id", b.getId());
			map.put("lineName", b.getLineName());
			map.put("linePrice", b.getPrice());
			map.put("list", bl2);//整个路线的站点
			lines.add(map);
			lineIds.add(b.getId());
		}
		BusLineMsgInfoExample be2 = new BusLineMsgInfoExample();
		BusLineMsgInfoExample.Criteria bc2= be2.createCriteria();
		bc2.andStationIdEqualTo(startId);
		bc2.andLineIdIn(lineIds);
		List<BusLineMsgInfo> bl2 = busLineMsgInfoMapper.selectByExample(be2);
	
		
		if(bl2.size()==0){
			return rs;
		}
		getAllPath2(rs,lines, new ArrayList<Map<String,Object>>(), startId, bl2.get(0).getLineId(), endId);
		
		Collections.sort(rs, new Comparator<Map<String,Object>>() {
		    @Override
		    public int compare(Map<String,Object> u1, Map<String,Object> u2) {
		      int diff = Integer.parseInt(u1.get("costTime").toString()) -Integer.parseInt(u2.get("costTime").toString());
		      if (diff > 0) {
		        return 1;
		      }else if (diff < 0) {
		        return -1;
		      }
		      return 0; //相等为0
		    }
		  }); // 按花费时间排序
		return rs;
		
	}
	/**
	 * 得到所有的线路
	 * @param rs      所有可抵达路线
	 * @param lines   所有线路
	 * @param startId  当前遍历的起点id
	 * @param endId 终点id
	 * sids:从起点到现在积累的站点
	 */
	public void getAllPath2(List<Map<String,Object>> rs,List<Map<String,Object>> lines,List<Map<String,Object>> sids,Integer startId,Integer startLineId,Integer endId){
		for(Map<String,Object> line:lines){
			Integer lineId = Integer.parseInt(line.get("id").toString());
			String lineName = line.get("lineName").toString();
			double linePrice= Double.parseDouble(line.get("linePrice").toString());
			List<BusLineMsgInfo> list = (List<BusLineMsgInfo>) line.get("list");
			if(list.size()>1){
				for(int i=0;i<list.size();i++){
					BusLineMsgInfo b = list.get(i);
					if(b.getStationId().equals(startId)){
						if(i==0){
							 getAllPath(rs, lines,sids, startId, startLineId, endId,
										list,i,i+1, lineId, linePrice, lineName);
						}else if(i==(list.size()-1)){
							 getAllPath(rs, lines,sids, startId, startLineId, endId,
										list,i,i-1, lineId, linePrice, lineName);
						}else{
							 getAllPath(rs, lines,sids, startId, startLineId, endId,
										list,i,i-1, lineId, linePrice, lineName);
							 getAllPath(rs, lines,sids, startId, startLineId, endId,
										list,i,i+1, lineId, linePrice, lineName);
						}
						break;
					}
				}
			}else{//该线路没有开发或者只有一个点，孤立的点不可抵达,直接retturn;
				return;
			}
			
		}
		
	}
	
	
	/**
	 * 探寻从某个点到下一个点是否到达终点，到达的话就加上到该点为止的路线，不可达的话就判断还能不能往下走了，可以的话就继续，否则就不继续跑了
	 * 
	 * @param rs
	 * @param lines
	 * @param sids2
	 * @param startId
	 * @param startLineId
	 * @param endId
	 * @param list
	 * @param currentIndex
	 * @param nextIndex
	 * @param lineId
	 * @param linePrice
	 * @param lineName
	 */
	private void getAllPath(List<Map<String,Object>> rs,List<Map<String,Object>> lines,List<Map<String,Object>> sids2,Integer startId,Integer startLineId,Integer endId,
			List<BusLineMsgInfo> list,Integer currentIndex,Integer nextIndex,Integer lineId,double linePrice,String lineName){
		List<Integer> lineIds  = new ArrayList<Integer>();
		for(Map<String,Object> l:lines){
			lineIds.add(Integer.parseInt(l.get("id").toString()));
		}
		BusLineMsgInfo nextS = list.get(nextIndex);
		boolean canAdd=true;
		List<Map<String,Object>> sids = new ArrayList<Map<String,Object>>();
		sids.addAll(sids2);
		for(Map<String,Object> smap:sids){
			if(smap.get("stationId").toString().equals(startId+"")){
				canAdd=false;//已经加过
				break;
			}
		}
		if(canAdd==true){
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("stationId",startId);
			StationInfo s = stationInfoMapper.selectByPrimaryKey(startId);
			if(startLineId.equals(lineId)==false&&sids2.size()>0){
				BusLineInfo newLine = busLineInfoMapper.selectByPrimaryKey(lineId);
				map.put("stationName",s.getStationName()+"(换乘“"+newLine.getLineName()+"”)");
			}else{
				BusLineInfo newLine = busLineInfoMapper.selectByPrimaryKey(lineId);
				map.put("stationName",s.getStationName()+"(“"+newLine.getLineName()+"”)");	
			}
			
			map.put("lineId",lineId);
			sids.add(map);
		}
	
		if(nextS.getStationId().equals(endId)){//下一个站点就是终点
			boolean canAdd2=true;
			for(Map<String,Object> smap:sids){
				if(smap.get("stationId").toString().equals(endId+"")){
					canAdd2=false;//已经加过
					break;
				}
			}
			if(canAdd2==true){
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("stationId",endId);
				StationInfo s = stationInfoMapper.selectByPrimaryKey(endId);
				BusLineInfo newLine = busLineInfoMapper.selectByPrimaryKey(lineId);
				map.put("stationName",s.getStationName()+"(“"+newLine.getLineName()+"”)");
				map.put("lineId",lineId);
				sids.add(map);
			}
			
			
			Map<String,Object> map2  =new HashMap<String,Object>();
			map2.put("list", sids);
			map2.put("stationNum", sids.size());
			List<Integer> allLinesIds = new ArrayList<Integer>();
			for(Map<String,Object> sid:sids){
				allLinesIds.add(Integer.parseInt(sid.get("lineId").toString()));
			}
			BusLineInfoExample le = new BusLineInfoExample();
			BusLineInfoExample.Criteria lc = le.createCriteria();
			lc.andIdIn(allLinesIds);
			List<BusLineInfo> clines = busLineInfoMapper.selectByExample(le);
			//计算换乘次数
			int times=clines.size()-1;
			double totalPrice=0.0;
			for(BusLineInfo l:clines){
				totalPrice+=l.getPrice();
			}
			map2.put("changeTimes", times);
			map2.put("totalPrice", totalPrice);
			map2.put("costTime",times*20+list.size()*5);//每换乘一次加20分钟，每个站平均5分钟
			rs.add(map2);
		}else{
			
			BusLineMsgInfoExample be2 = new BusLineMsgInfoExample();
			BusLineMsgInfoExample.Criteria bc2= be2.createCriteria();
			bc2.andStationIdEqualTo(list.get(nextIndex).getStationId());
			bc2.andLineIdNotEqualTo(lineId);
			if(lineIds.size()>0){
				bc2.andLineIdIn(lineIds);
			}
			List<BusLineMsgInfo> ml= busLineMsgInfoMapper.selectByExample(be2);
			if(ml.size()>0){//可换乘的站点
				for(BusLineMsgInfo b2:ml){
					
					BusLineMsgInfoExample be3 = new BusLineMsgInfoExample();
					BusLineMsgInfoExample.Criteria bc3= be3.createCriteria();
					bc3.andLineIdEqualTo(b2.getLineId());
					be3.setOrderByClause("no asc");//根据编号排序
					List<BusLineMsgInfo> ml3= busLineMsgInfoMapper.selectByExample(be3);
					int currentIndex2 = -1;
					for(int j=0;j<ml3.size();j++){
						BusLineMsgInfo m3 = ml3.get(j);
						if(m3.getStationId().equals(nextS.getStationId())){
							currentIndex2 =j;
							break;
						}
						
					}
					
					
					BusLineInfo line2 = busLineInfoMapper.selectByPrimaryKey(b2.getLineId());
					
					if(currentIndex2!=ml3.size()-1&&currentIndex2!=0){
						if(checkIsAdd(sids, ml3.get(currentIndex2+1).getStationId())){
							getAllPath(rs,lines,sids,ml3.get(currentIndex2).getStationId(), lineId, endId,
									 ml3,currentIndex2, currentIndex2+1, b2.getLineId(), line2.getPrice(), line2.getLineName());
						}
						if(checkIsAdd(sids, ml3.get(currentIndex2-1).getStationId())){
							getAllPath(rs,lines,sids, ml3.get(currentIndex2).getStationId(), lineId, endId,
									 ml3,currentIndex2, currentIndex2-1, b2.getLineId(), line2.getPrice(), line2.getLineName());
						}
						
						
					}else{
						if(currentIndex2==0){
							if(checkIsAdd(sids, ml3.get(currentIndex2+1).getStationId())){
								getAllPath(rs,lines,sids, ml3.get(currentIndex2).getStationId(), lineId, endId,
										 ml3,currentIndex2, currentIndex2+1, b2.getLineId(), line2.getPrice(), line2.getLineName());
							}
						}
						if(currentIndex2==ml3.size()-1){
							if(checkIsAdd(sids, ml3.get(currentIndex2-1).getStationId())){
								getAllPath(rs,lines,sids, ml3.get(currentIndex2).getStationId(), lineId, endId,
										 ml3,currentIndex2, currentIndex2-1, b2.getLineId(), line2.getPrice(), line2.getLineName());
							}
							
						}
					}
					
				
				}
				
				
				
			}
			
			if(nextIndex==(list.size()-1)||nextIndex==0){//下一站是终点
				return;
				
			}else{
				if(currentIndex<nextIndex){
					if(checkIsAdd(sids, list.get(nextIndex+1).getStationId())){
						getAllPath(rs,lines,sids, nextS.getStationId(), lineId, endId,
								 list,nextIndex, nextIndex+1, list.get(nextIndex+1).getLineId(), linePrice, lineName);
					}
					
				}else{
				
					if(checkIsAdd(sids, list.get(nextIndex-1).getStationId())){
						getAllPath(rs,lines,sids, nextS.getStationId(), lineId, endId,
								 list,nextIndex, nextIndex-1, list.get(nextIndex-1).getLineId(), linePrice, lineName);
					}
					
				}
			
			}
			
			
		}
	}
	
	private boolean checkIsAdd(List<Map<String,Object>> sids,Integer staId){
		boolean canAdd2=true;
		for(Map<String,Object> smap:sids){
			if(smap.get("stationId").toString().equals(staId+"")){
				canAdd2=false;//已经加过
				break;
			}
		}
		
		return canAdd2;
	}
	
	
}
