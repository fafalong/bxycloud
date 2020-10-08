package com.boxiaoyun.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxiaoyun.common.model.PageParams;
import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.mybatis.base.controller.BaseController;
import com.boxiaoyun.common.security.BaseUserDetails;
import com.boxiaoyun.common.security.SecurityHelper;
import com.boxiaoyun.common.utils.BeanPlusUtil;
import com.boxiaoyun.common.utils.BizAssert;
import com.boxiaoyun.common.utils.SuperEntity;
import com.boxiaoyun.common.utils.TreeUtil;
import com.boxiaoyun.system.client.model.dto.OrgSaveDTO;
import com.boxiaoyun.system.client.model.dto.OrgUpdateDTO;
import com.boxiaoyun.system.client.model.entity.SystemOrg;
import com.boxiaoyun.system.client.service.ISystemOrgServiceClient;
import com.boxiaoyun.system.service.SystemOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.boxiaoyun.common.utils.StrPool.DEF_PARENT_ID;
import static com.boxiaoyun.common.utils.StrPool.DEF_ROOT_PATH;
@Api(tags = "系统组织管理")
@RestController
public class SystemOrgController extends BaseController<SystemOrgService, SystemOrg> implements ISystemOrgServiceClient{

    @Autowired
    private SystemOrgService systemOrgService;
    @Autowired
    private TokenStore tokenStore;
    /**
     * 获取系统组织列表
     *
     * @return
     */
    @GetMapping("/sysOrg/page")
    @Override
    public ResultBody<Page<SystemOrg>> getPage(@RequestParam Map<String, Object> map) {
        return ResultBody.ok().data(bizService.findPage(new PageParams(map)));
    }
    /**
     * 新增组织
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增组织", notes = "新增组织不为空的字段")
    @PostMapping("/sysOrg/save")
    public ResultBody save(@RequestBody @Validated OrgSaveDTO data) {

        SystemOrg systemOrg = BeanPlusUtil.toBean(data, SystemOrg.class);
        if (systemOrg.getParentId() == null || systemOrg.getParentId() <= 0) {
            systemOrg.setParentId(DEF_PARENT_ID);
            systemOrg.setTreePath(DEF_ROOT_PATH);
        } else {
            SystemOrg parent = systemOrgService.getById(systemOrg.getParentId());
            BizAssert.notNull(parent, "父组织不能为空");

            systemOrg.setTreePath(StringUtils.join(parent.getTreePath(), parent.getId(), DEF_ROOT_PATH));
        }
        return ResultBody.success();
    }

    /**
     * 查询系统所有的组织树
     *
     * @param status 状态
     * @return
     * @author bxy
     * @date 2019-07-29 11:59
     */
    @ApiOperation(value = "查询系统所有的组织树", notes = "查询系统所有的组织树")
    @GetMapping("/sysOrg/tree")
    //@SysLog("查询系统所有的组织树")
    public ResultBody<List<SystemOrg>> tree(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "status", required = false) Boolean status) {
        List<SystemOrg> list = bizService.findList();
        return this.success(TreeUtil.buildTree(list));
    }

    /**
     * 删除组织
     *
     * @param orgId
     * @return
     */
    @PostMapping("/sysOrg/remove")
    @Override
    public ResultBody remove(@RequestParam(value = "orgId") Long orgId) {
        bizService.remove(orgId);
        return ResultBody.ok();
    }

    /**
     * 修改组织
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改组织", notes = "修改组织不为空的字段")
    @PostMapping("/sysOrg/update")
    //@SysLog("修改组织")
    //public ResultBody update(@RequestBody @Validated(SuperEntity.Update.class) OrgUpdateDTO data) {
    public ResultBody update(@RequestBody OrgUpdateDTO data) {
        SystemOrg org = BeanPlusUtil.toBean(data, SystemOrg.class);
        bizService.update(org);
        return ResultBody.success();//.success(org);
    }
    /**
     * 获取用户详情
     *
     * @param orgId
     * @return
     */
    @GetMapping("/sysOrg/info")
    @Override
    public ResultBody<SystemOrg> get(Long orgId) {
        return ResultBody.success(bizService.getById(orgId));
    }
    /**
     * 获取所有组织列表
     *
     * @return
     */
    @GetMapping("/sysOrg/list")
    @Override
    public ResultBody<List<SystemOrg>> getList() {
        return ResultBody.success(bizService.findList());
    }

}
