package com.validator.responsebodyadvice.demo.service.impl;

import com.google.common.collect.Lists;
import com.validator.responsebodyadvice.demo.dto.BrandDto;
import com.validator.responsebodyadvice.demo.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BrandService实现类
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Override
    public List<BrandDto> listAllBrand() {
        return Lists.newArrayList();
    }

    @Override
    public int createBrand(BrandDto brandDto) {
        return 0;
    }

    @Override
    public int updateBrand(Long id, BrandDto brandDto) {
        return 0;
    }

    @Override
    public int deleteBrand(Long id) {
        return 0;
    }

    @Override
    public List<BrandDto> listBrand(int pageNum, int pageSize) {
        return Lists.newArrayList();
    }

    @Override
    public BrandDto getBrand(Long id) {
        return new BrandDto();
    }
}
