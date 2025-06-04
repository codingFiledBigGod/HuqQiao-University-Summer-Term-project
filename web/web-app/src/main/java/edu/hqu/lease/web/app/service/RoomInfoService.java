package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.RoomInfo;
import edu.hqu.lease.web.app.vo.room.RoomDetailVo;
import edu.hqu.lease.web.app.vo.room.RoomItemVo;
import edu.hqu.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.ExecutionException;


public interface RoomInfoService extends IService<RoomInfo> {
    IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo);

    IPage<RoomItemVo> pageItemById(Page<RoomItemVo> page, Long id);

    RoomDetailVo getDetailByid(Long id) throws ExecutionException, InterruptedException;
}
