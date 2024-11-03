package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.institution;
import com.shxy.models.dbBean.user;
import com.shxy.models.dbBean.word;
import com.shxy.models.dbBean.wordType;
import com.shxy.models.paramsBean.fiber.fiberQuery;
import com.shxy.models.paramsBean.institution.addInstitutionParam;
import com.shxy.models.paramsBean.institution.editInstitutionParam;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.paramsBean.user.editPermissionParam;
import com.shxy.models.paramsBean.word.addWordParam;
import com.shxy.models.paramsBean.word.addWordTypeParam;
import com.shxy.models.paramsBean.word.wordQuery;
import com.shxy.models.paramsBean.word.wordTypeQuery;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.fiberItem;
import com.shxy.models.views.wordItem;
import com.shxy.models.views.wordTypeItem;
import com.shxy.sudatqd.dbService.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600) //允许跨域
@RequestMapping("/Word")
public class wordController {
    @Autowired
    private userService userService;
    @Autowired
    private wordService wordService;
    @Autowired
    private wordTypeService wordTypeService;
    @Autowired
    private pictureService pictureService;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @ApiOperation(value = "获取敏感词列表",notes = "获取敏感词列表")
    @PostMapping("/getWordList")
    @Transactional
    public ResultMsg<List<word>> getWordList() {
        try {
            QueryWrapper<word> list = new QueryWrapper<>();
            list.eq("state", 0);
            List<word> oneList = wordService.list(list);
            return new ResultMsg<>(true, "获取成功", 200, oneList);
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑敏感词类型",notes = "编辑敏感词基本信息")
    @PostMapping("/editWord")
    @Transactional
    public ResultMsg<Boolean> editWord(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<word> wordUpdateWrapper = new UpdateWrapper<>();
                wordUpdateWrapper.eq("wordId",data.getId());
                wordUpdateWrapper.set("type", data.getStatus());
                boolean update = wordService.update(wordUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "敏感词类型已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑敏感词状态",notes = "编辑敏感词状态")
    @PostMapping("/changeWordStatus")
    @Transactional
    public ResultMsg<Boolean> changeWordStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<word> wordUpdateWrapper = new UpdateWrapper<>();
                wordUpdateWrapper.eq("wordId",data.getId());
                wordUpdateWrapper
                        .set("state", data.getStatus());
                boolean update = wordService.update(wordUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "敏感词状态已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "新增敏感词",notes = "新增敏感词")
    @PostMapping("/addWord")
    @Transactional
    public ResultMsg<Boolean> addWord(@RequestBody addWordParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                //当前登录用户的id
                QueryWrapper<user> list = new QueryWrapper<>();
                list.eq("username",getUsernameFromToken(token_id));
                Integer oneId=userService.getOne(list).getUserId();
                //新增敏感词
                word addnew = new word();
                addnew.setWordName(data.getWordName());
                addnew.setType(data.getType());
                addnew.setState(0);
                addnew.setCreatorId(oneId);
                addnew.setCreationTime(DateTime.now());
                // 新增用户，返回结果
                boolean update = wordService.save(addnew);
                if (update) {
                    return new ResultMsg<>(true, "已成功添加敏感词！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取敏感词类型列表",notes = "获取敏感词类型列表")
    @PostMapping("/getWordTypeList")
    @Transactional
    public ResultMsg<List<wordType>> getWordTypeList() {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                QueryWrapper<wordType> list = new QueryWrapper<>();
                list.eq("state", 0);
                List<wordType> oneList = wordTypeService.list(list);
                return new ResultMsg<>(true, "获取成功", 200, oneList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑敏感词类型描述",notes = "编辑敏感词类型描述")
    @PostMapping("/editWordType")
    @Transactional
    public ResultMsg<Boolean> editWordType(@RequestBody editPermissionParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<wordType> wordTypeUpdateWrapper = new UpdateWrapper<>();
                wordTypeUpdateWrapper.eq("wordTypeId",data.getId());
                wordTypeUpdateWrapper.set("description", data.getDescription());
                boolean update = wordTypeService.update(wordTypeUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "敏感词类型描述已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "编辑敏感词类型状态",notes = "编辑敏感词类型状态")
    @PostMapping("/changeWordTypeStatus")
    @Transactional
    public ResultMsg<Boolean> changeWordTypeStatus(@RequestBody changeStatus data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                UpdateWrapper<wordType> wordTypeUpdateWrapper = new UpdateWrapper<>();
                wordTypeUpdateWrapper.eq("wordTypeId",data.getId());
                wordTypeUpdateWrapper
                        .set("state", data.getStatus());
                boolean update = wordTypeService.update(wordTypeUpdateWrapper);
                if (update) {
                    return new ResultMsg<>(true, "敏感词类型状态已更新！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "新增敏感词类型",notes = "新增敏感词类型")
    @PostMapping("/addWordType")
    @Transactional
    public ResultMsg<Boolean> addWordType(@RequestBody addWordTypeParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                //当前登录用户的id
                QueryWrapper<user> list = new QueryWrapper<>();
                list.eq("username",getUsernameFromToken(token_id));
                Integer oneId=userService.getOne(list).getUserId();
                //新增敏感词
                wordType addnew = new wordType();
                addnew.setTypeName(data.getTypeName());
                addnew.setState(0);
                addnew.setCreatorId(oneId);
                addnew.setCreationTime(DateTime.now());
                addnew.setDescription(data.getDescription());
                // 新增用户，返回结果
                boolean update = wordTypeService.save(addnew);
                if (update) {
                    return new ResultMsg<>(true, "已成功添加敏感词类型！", 200, update);
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取敏感词分页查询列表",notes = "获取敏感词分页查询列表")
    @PostMapping("/getWordPageList")
    @Transactional
    public ResultMsg<PageInfo<wordItem>> getWordPageList(@RequestBody wordQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<wordItem> pageList= wordService.wordPage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取敏感词类型分页查询列表",notes = "获取敏感词类型分页查询列表")
    @PostMapping("/getWordTypePageList")
    @Transactional
    public ResultMsg<PageInfo<wordTypeItem>> getWordTypePageList(@RequestBody wordTypeQuery data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                PageInfo<wordTypeItem> pageList= wordTypeService.wordTypePage(data);
                return new ResultMsg<>(true, "获取成功", 200, pageList);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    //解析当前登录的用户名
    public String getUsernameFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSign);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);
        return decodedJWT.getClaim("username").asString();
    }

}
