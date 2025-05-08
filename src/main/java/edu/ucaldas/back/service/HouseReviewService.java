package edu.ucaldas.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.repository.IHouseReviewRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;

@Service
public class HouseReviewService {
    @Autowired
    private IHouseReviewRepository houseReviewRepository;

    @Autowired
    private ValidationsHouse validationsHouse;

    public void getReviewsHouse(Long id){
        
    }

}
