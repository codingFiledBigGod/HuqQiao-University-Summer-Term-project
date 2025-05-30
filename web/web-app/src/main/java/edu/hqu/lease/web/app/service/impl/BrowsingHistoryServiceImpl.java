package edu.hqu.lease.web.app.service.impl;

import edu.hqu.lease.common.login.LoginUserHolder;
import edu.hqu.lease.model.entity.BrowsingHistory;
import edu.hqu.lease.web.app.mapper.BrowsingHistoryMapper;
import edu.hqu.lease.web.app.service.BrowsingHistoryService;
import edu.hqu.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {
    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;
    @Override
    public IPage<HistoryItemVo> pageItemByUserId(Page<HistoryItemVo> page, Long userId) {
        return browsingHistoryMapper.pageItemByUserId(page,userId);
    }

    @Override
    public void saveHistory(Long id) {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        BrowsingHistory browsingHistory = new BrowsingHistory();
        browsingHistory.setUserId(userId);
        browsingHistory.setRoomId(id);
        browsingHistory.setBrowseTime(new Date());
        browsingHistoryMapper.insert(browsingHistory);
    }
}