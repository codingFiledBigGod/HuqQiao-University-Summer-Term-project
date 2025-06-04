package edu.hqu.lease.web.app.mapper;

import edu.hqu.lease.model.entity.FeeValue;
import edu.hqu.lease.web.app.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface FeeValueMapper extends BaseMapper<FeeValue> {

    List<FeeValueVo> listFeeByRoomId(Long roomId);
}




