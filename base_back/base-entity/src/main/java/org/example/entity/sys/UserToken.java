package org.example.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户Token
 * </p>
 *
 * @author chenchong
 * @since 2019-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_token")
public class UserToken extends Model<UserToken> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * token
     */
    private String token;
    /**
     * code
     */
    private String loginName;
    /**
     * name
     */
    private String userName;

    /**
     * 过期时间
     */
    private Date expireTime;

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
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    private String deptCode;
    private String deptName;

    private Integer deptId;

    /**
     * 是否计生办人员   '0=否；1=是 ; 2=是质检员',
     */
    private Integer isFpUser;

    /**
     *      '1=儿童；2=老年；3=其他；4=全部',
     */
    private Integer useType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
