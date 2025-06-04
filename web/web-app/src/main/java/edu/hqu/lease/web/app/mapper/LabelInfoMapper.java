package edu.hqu.lease.web.app.mapper;

import edu.hqu.lease.model.entity.LabelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    List<LabelInfo> listLabelByRoomId(Long roomId);
}




