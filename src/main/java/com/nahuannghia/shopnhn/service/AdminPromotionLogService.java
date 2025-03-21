package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.AdminPromotionLog;
import com.nahuannghia.shopnhn.repository.AdminPromotionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPromotionLogService {
    @Autowired
    private AdminPromotionLogRepository adminPromotionLogRepository;
    public AdminPromotionLog addPromotionLog(AdminPromotionLog adminPromotionLog){
        try{
            return adminPromotionLogRepository.save(adminPromotionLog);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<AdminPromotionLog> getAllPromotionLog(){
        List<AdminPromotionLog> list = adminPromotionLogRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No promotion log");
        return list;
    }
    public AdminPromotionLog getPromotionLog(int promotionLogId){
        return adminPromotionLogRepository.findById(promotionLogId).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public AdminPromotionLog updatePromotionLog(int promotionLogId, AdminPromotionLog adminPromotionLog){
        AdminPromotionLog existing  = getPromotionLog(promotionLogId);
        existing.setLogDate(adminPromotionLog.getLogDate());
        existing.setPromotionProduct(adminPromotionLog.getPromotionProduct());
        try{
            return adminPromotionLogRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deletePromotionLog(int promotionLogId){
        AdminPromotionLog existing = getPromotionLog(promotionLogId);
        try {
            adminPromotionLogRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
