package edu.hqu.lease.web.app.service.impl;

import edu.hqu.lease.model.entity.ApartmentInfo;
import edu.hqu.lease.model.entity.GraphInfo;
import edu.hqu.lease.model.entity.ViewAppointment;
import edu.hqu.lease.model.enums.ItemType;
import edu.hqu.lease.web.app.mapper.ApartmentInfoMapper;
import edu.hqu.lease.web.app.mapper.GraphInfoMapper;
import edu.hqu.lease.web.app.mapper.ViewAppointmentMapper;
import edu.hqu.lease.web.app.service.ApartmentInfoService;
import edu.hqu.lease.web.app.service.ViewAppointmentService;
import edu.hqu.lease.web.app.vo.apartment.ApartmentDetailVo;
import edu.hqu.lease.web.app.vo.apartment.ApartmentItemVo;
import edu.hqu.lease.web.app.vo.appointment.AppointmentDetailVo;
import edu.hqu.lease.web.app.vo.appointment.AppointmentItemVo;
import edu.hqu.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private ViewAppointmentMapper viewAppointmentMapper;

    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Override
    public List<AppointmentItemVo> listItem(Long userId) {

        LambdaQueryWrapper<ViewAppointment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ViewAppointment::getUserId, userId);
        List<ViewAppointment> viewAppointments=viewAppointmentMapper.selectList(wrapper);
        List<AppointmentItemVo> res=new ArrayList<>();
        for (ViewAppointment viewAppointment : viewAppointments) {
            AppointmentItemVo appointmentItemVo=new AppointmentItemVo();
            appointmentItemVo.setId(viewAppointment.getId());
            ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(viewAppointment.getApartmentId());
            appointmentItemVo.setApartmentName(apartmentInfo.getName());
            //设置公寓图片列表
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemId, apartmentInfo.getId()).eq(GraphInfo::getItemType, ItemType.APARTMENT);
            List<GraphInfo> graphInfos = graphInfoMapper.selectList(graphQueryWrapper);
            List<GraphVo> graphVos = new ArrayList<>();
            for (GraphInfo graphInfo : graphInfos) {
                GraphVo graphVo = new GraphVo();
                graphVo.setName(graphInfo.getName());
                graphVo.setUrl(graphInfo.getUrl());
                graphVos.add(graphVo);
            }
            appointmentItemVo.setGraphVoList(graphVos);
            appointmentItemVo.setAppointmentTime(viewAppointment.getAppointmentTime());
            appointmentItemVo.setAppointmentStatus(viewAppointment.getAppointmentStatus());
            res.add(appointmentItemVo);
        }

        return res;
    }

    @Override
    public AppointmentDetailVo getDetailById(Long id) throws ExecutionException, InterruptedException {

        AppointmentDetailVo res=new AppointmentDetailVo();
        ViewAppointment viewAppointment = viewAppointmentMapper.selectById(id);
        BeanUtils.copyProperties(viewAppointment, res);
        ApartmentItemVo apartmentItemVo=new ApartmentItemVo();
        ApartmentInfo apartmentInfo = apartmentInfoService.getById(viewAppointment.getApartmentId());
        BeanUtils.copyProperties(apartmentInfo,apartmentItemVo);

        ApartmentDetailVo detailById = apartmentInfoService.getDetailById(viewAppointment.getApartmentId());

        apartmentItemVo.setGraphVoList(detailById.getGraphVoList());
        apartmentItemVo.setLabelInfoList(detailById.getLabelInfoList());
        apartmentItemVo.setMinRent(detailById.getMinRent());

        res.setApartmentItemVo(apartmentItemVo);
        return res;
    }
}




