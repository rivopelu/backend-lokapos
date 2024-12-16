package com.lokapos.annotations;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface BaseControllerImpl {
}

