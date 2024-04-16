package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplyService {

  @Autowired
  private SupplyRepository supplyRepository;

}
