package com.shxy.sudatqd.controllers;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.shxy.models.dbBean.*;
import com.shxy.models.paramsBean.*;
import com.shxy.models.paramsBean.operation.commentParam;
import com.shxy.models.paramsBean.operation.complainParam;
import com.shxy.models.paramsBean.personalCenter.userCommentQuery;
import com.shxy.models.paramsBean.user.changeStatus;
import com.shxy.models.returnBean.ResultMsg;
import com.shxy.models.views.operation.commentItem;
import com.shxy.models.views.operation.replyDTO;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*", maxAge = 3600) //允许跨域
@RequestMapping("/Operation")
public class operationController {
    @Autowired
    private userService userService;
    @Autowired
    private commentService commentService;
    @Autowired
    private replyService replyService;
    @Autowired
    private reportCommentService reportCommentService;
    @Autowired
    private feedbackService feedbackService;
    @Autowired
    private rolePermissionService rolePermissionService;
    @Autowired
    private wordService wordService;
    @Autowired
    private auditService auditService;
    @Autowired
    private wordMapper wordMapper;
    @Autowired
    private userRoleMapper userRoleMapper;
    @Autowired
    private rolePermissionMapper rolePermissionMapper;
    @Value("${jwtSign}")
    String jwtSign;

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    @ApiOperation(value = "发表评论",notes = "发表评论")
    @PostMapping("/makeComment")
    @Transactional
    public ResultMsg<Boolean> makeComment(@RequestBody commentParam data) {
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
                //校验评论内容是否包含敏感词
                QueryWrapper<word> queryWrapper = new QueryWrapper<>();
                List<word> words = wordMapper.selectList(queryWrapper);
                int res = 0;
                for (word word : words) {
                    if (data.getContent().contains(word.getWordName())) {
                        res = res+1;
                    }
                }
                //判断是否包含敏感词
                if(res==0){
                    // 新增评论
                    comment addnew = new comment();
                    addnew.setUserId(oneId);
                    addnew.setObjectType(data.getType());// 1代表档案，2代表纸张，3代表机构，4代表纤维，5代表成分
                    addnew.setObjectId(data.getId());
                    addnew.setCommentState(0);
                    addnew.setCommentTime(DateTime.now());
                    addnew.setCommentContent(data.getContent());
                    // 新增评论，返回结果
                    boolean update = commentService.save(addnew);
                    if (update) {
                        return new ResultMsg<>(true, "您的评论已成功发布！", 200, true);
                    } else {
                        return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                    }
                } else {
                    return new ResultMsg<>(true, "您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子", 205, null);
                }

            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "发表回复",notes = "发表回复")
    @PostMapping("/makeReply")
    @Transactional
    public ResultMsg<Boolean> makeReply(@RequestBody commentParam data) {
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
                //校验回复内容是否包含敏感词
                QueryWrapper<word> queryWrapper = new QueryWrapper<>();
                List<word> words = wordMapper.selectList(queryWrapper);
                int res = 0;
                for (word word : words) {
                    if (data.getContent().contains(word.getWordName())) {
                        res = res+1;
                    }
                }
                //判断是否包含敏感词
                if(res==0){
                    // 新增回复
                    reply addnew = new reply();
                    addnew.setUserId(oneId);
                    addnew.setType(data.getType());// 0代表回复的评论、1代表回复的回复
                    addnew.setReplyyId(data.getId());
                    addnew.setReplyState(1);
                    addnew.setReplyTime(DateTime.now());
                    addnew.setReplyContent(data.getContent());
                    // 新增回复，返回结果
                    boolean update = replyService.save(addnew);
                    if (update) {
                        return new ResultMsg<>(true, "您的回复已成功发布！", 200, true);
                    } else {
                        return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                    }
                } else {
                    return new ResultMsg<>(true, "您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子", 205, null);
                }

            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "投诉评论/回复",notes = "投诉评论/回复")
    @PostMapping("/makeReport")
    @Transactional
    public ResultMsg<Boolean> makeReport(@RequestBody complainParam data) {
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
                //判断当前用户是否拥有审核权限
                List<Integer> permissions = rolePermissionService.userPermission(oneId);
                int status = 0;
                if(permissions.contains(4)){
                    status = 1;
                }
                // 新增表记录
                reportComment addnew = new reportComment();
                addnew.setCommentId(data.getId());
                addnew.setReportReasonType(data.getType());
                addnew.setReporterId(oneId);
                addnew.setReportContentType(data.getContentType());
                addnew.setContent(data.getContent());
                addnew.setReportTime(DateTime.now());
                addnew.setReportState(status);
                // 新增回复，返回结果
                boolean update = reportCommentService.save(addnew);
                if(!permissions.contains(4)){
                    auditService.addAudit(2,addnew.getReportId());
                }
                if (update) {
                    if(status==0){
                        return new ResultMsg<>(true, "您的投诉已提交至管理员处，请耐心等待审核！", 200, true);
                    } else {
                        return new ResultMsg<>(true, "您的投诉已提交！", 200, true);
                    }
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "反馈",notes = "反馈")
    @PostMapping("/feedback")
    @Transactional
    public ResultMsg<Boolean> feedback(@RequestBody commentParam data) {
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
                //判断当前用户是否拥有审核权限
                List<Integer> permissions = rolePermissionService.userPermission(oneId);
                int status = 0;
                if(permissions.contains(4)){
                    status = 1;
                }
                // 新增表记录
                feedback addnew = new feedback();
                addnew.setUserId(oneId);
                addnew.setObjectId(data.getId());
                addnew.setFeedbackType(data.getType());
                addnew.setContent(data.getContent());
                addnew.setCreationTime(DateTime.now());
                addnew.setState(status);
                // 新增反馈，返回结果
                boolean update = feedbackService.save(addnew);
                if(!permissions.contains(4)){
                    auditService.addAudit(3,addnew.getFeedbackId());
                }
                if (update) {
                    if(status==0){
                        return new ResultMsg<>(true, "您的反馈已提交至管理员处，请耐心等待审核！", 200, true);
                    } else {
                        return new ResultMsg<>(true, "您的反馈已提交！", 200, true);
                    }
                } else {
                    return new ResultMsg<>(true, "操作失败，请重试", 205, null);
                }
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取一条详情中的评论和回复",notes = "获取一条详情中的评论和回复")
    @PostMapping("/getCommentReplyList")
    @Transactional
    public ResultMsg<List<replyDTO>> getCommentReplyList(@RequestBody changeStatus data) {
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

                List<replyDTO> res=commentService.getCommentReplyList(data.getId(),data.getStatus());
                return new ResultMsg<>(true, "获取成功！", 200, res);
            }
        } catch (Exception e) {
            return new ResultMsg<>(false, e.getMessage(), 500, null);
        }
    }

    @ApiOperation(value = "获取被举报的评论列表",notes = "获取被举报的评论列表")
    @PostMapping("/getCommentShenList")
    @Transactional
    public ResultMsg<PageInfo<reportComment>> getCommentShenList(@RequestBody BasicQueryParam data) {
        try {
            //拿到token进行解析，拿到当前登录用户信息，返回即可
            String token_id = getHttpServletRequest().getHeader("Authorization");
            if (token_id == null || token_id.isEmpty()) {
                return new ResultMsg<>(true, "登录已失效，请重新登录！", 200, null);
            } else {
                //当前登录用户的id
//                QueryWrapper<reportComment> list = new QueryWrapper<>();

                PageInfo<reportComment> res = reportCommentService.reportCommentItemList(data);
                //返回结果
                return new ResultMsg<>(true, "获取成功", 200, res);
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
    //获取用户权限
}
