package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.RoomInfo;
import edu.hqu.lease.web.app.vo.room.RoomDetailVo;
import edu.hqu.lease.web.app.vo.room.RoomItemVo;
import edu.hqu.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.ExecutionException;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface RoomInfoService extends IService<RoomInfo> {
    IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo);

    IPage<RoomItemVo> pageItemById(Page<RoomItemVo> page, Long id);

    RoomDetailVo getDetailByid(Long id) throws ExecutionException, InterruptedException;
}
