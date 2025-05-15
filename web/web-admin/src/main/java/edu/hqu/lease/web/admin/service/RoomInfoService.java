package edu.hqu.lease.web.admin.service;

import edu.hqu.lease.model.entity.RoomInfo;
import edu.hqu.lease.web.admin.vo.room.RoomDetailVo;
import edu.hqu.lease.web.admin.vo.room.RoomItemVo;
import edu.hqu.lease.web.admin.vo.room.RoomQueryVo;
import edu.hqu.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {

    void updateRoomInfo(RoomSubmitVo roomSubmitVo);

    IPage<RoomItemVo> selectPage(Page<RoomItemVo> page, RoomQueryVo queryVo);


    RoomDetailVo getDetailById(Long id);

    void removeRoomById(Long id);
}
