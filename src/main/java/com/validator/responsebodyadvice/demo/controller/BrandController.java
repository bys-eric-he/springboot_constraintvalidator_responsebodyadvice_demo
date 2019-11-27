package com.validator.responsebodyadvice.demo.controller;

import com.validator.responsebodyadvice.demo.dto.BrandDto;
import com.validator.responsebodyadvice.demo.exception.BusinessException;
import com.validator.responsebodyadvice.demo.response.ResponseResult;
import com.validator.responsebodyadvice.demo.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 品牌管理controller
 */
@Api(tags = "BrandController", description = "品牌管理接口")
@RestController
@ResponseResult
@RequestMapping("/api/v1")
public class BrandController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "添加品牌")
    @RequestMapping(value = "/brand/create", method = RequestMethod.POST)
    public Integer createBrand(@Validated @RequestBody BrandDto brand, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            LOGGER.error("Parameter is Not Validated! Message ->{}", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            throw new BusinessException(String.format("Parameter is Not Validated! Message ->%s", Objects.requireNonNull(result.getFieldError()).getDefaultMessage()));
        }

        return brandService.createBrand(brand);
    }

    @ApiOperation(value = "获取所有品牌")
    @RequestMapping(value = "/brand/list", method = RequestMethod.GET)
    public List<BrandDto> createBrand() throws Exception {

        return brandService.listAllBrand();
    }
}
