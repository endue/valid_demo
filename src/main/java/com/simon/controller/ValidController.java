package com.simon.controller;

import com.simon.util.ErrorCode;
import com.simon.valid.RegExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @data: 2020/6/10 17:21
 * @author: simon
 * @version:
 * @description:
 */
@RestController
@RequestMapping("/valid")
public class ValidController {

    @Valid
    @RequestMapping(value = "test_long",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String testLong(
        @RequestParam(value = "stu_id",required = false) @NotNull(message = ErrorCode.EMPTY_CODE) @Pattern(regexp = RegExpression.RANGE_1_10,message = ErrorCode.TOO_LONG_CODE) String stu_id,
        @RequestParam(value = "start_time",required = false) @Pattern(regexp = RegExpression.DATE_TIME,message = ErrorCode.ERROR_CODE) String start_time
    ){
        return stu_id;
    }
}
