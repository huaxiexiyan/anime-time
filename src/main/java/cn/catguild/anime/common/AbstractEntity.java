package cn.catguild.anime.common;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xiyan
 * &#064;date  2023/7/31 17:26
 */
@Data
public abstract class AbstractEntity{

	@TableId(type = IdType.INPUT)
	@JsonDeserialize(as = Long.class)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

    /**
     * 创建时间，更新不加入
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(updateStrategy= FieldStrategy.NEVER)
    private LocalDateTime createdTime;

	/**
	 * 创建人，更新不加入
	 */
	@TableField(updateStrategy= FieldStrategy.NEVER)
    @JsonIgnore
    private Long createdBy;

    /**
     * 最后修改时间
     **/
	@TableField(update="now()")
    @JsonIgnore
    private LocalDateTime lastModifiedTime;

	/**
	 * 记录最后修改人id
	 */
    @JsonIgnore
    private Long lastModifiedBy;

    /**
     * 记录逻辑删除时间
     */
    @JsonIgnore
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;

	/**
	 * 记录逻辑删除人id
	 */
    @JsonIgnore
    private Long deletedBy;

	/**
	 * 记录逻辑删除时间
	 */
	@JsonIgnore
	private LocalDateTime deletedTime;

}
