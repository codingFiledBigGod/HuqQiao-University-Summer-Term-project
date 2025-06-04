package edu.hqu.lease.web.app.mapper;

import edu.hqu.lease.model.entity.RoomInfo;
import edu.hqu.lease.web.app.vo.room.RoomItemVo;
import edu.hqu.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo);

    IPage<RoomItemVo> pageItemById(Page<RoomItemVo> page, Long id);
}