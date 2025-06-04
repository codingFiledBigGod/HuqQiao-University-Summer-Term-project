package edu.hqu.lease.web.app.mapper;

import edu.hqu.lease.model.entity.AttrValue;
import edu.hqu.lease.web.app.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface AttrValueMapper extends BaseMapper<AttrValue> {

    List<AttrValueVo> listRoomAttrById(Long roomId);
}




