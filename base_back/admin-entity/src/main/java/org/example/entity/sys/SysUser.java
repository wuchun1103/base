package org.example.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("loginName")
    private String loginName;

    @TableField("passWord")
    private String passWord;

    private String salt;

    @TableField("userName")
    private String userName;

    /**
     * 1=男；2=女
     */
    private Integer gender;

    @TableField("headUrl")
    private String headUrl;

    @TableField("userEmail")
    private String userEmail;

    @TableField("userPhone")
    private String userPhone;

    private Date birthday;

    @TableField("jobName")
    private String jobName;


    private String remark;


    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除  1：已删除  2：正常
     */
    private Integer delFlag;

    @TableField(exist = false)
    private List<UserRole> userRoles = new ArrayList<>();

    @TableField(exist = false)
    private String roleNames;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
