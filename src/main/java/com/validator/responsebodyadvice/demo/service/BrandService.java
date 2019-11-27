package com.validator.responsebodyadvice.demo.service;

import com.validator.responsebodyadvice.demo.dto.BrandDto;

import java.util.List;

public interface BrandService {
    List<BrandDto> listAllBrand();

    int createBrand(BrandDto brandDto);

    int updateBrand(Long id, BrandDto brandDto);

    int deleteBrand(Long id);

    List<BrandDto> listBrand(int pageNum, int pageSize);

    BrandDto getBrand(Long id);
}
