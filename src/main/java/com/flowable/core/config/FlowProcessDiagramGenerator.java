package com.flowable.core.config;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * 生成图片
 */
@Service
public class FlowProcessDiagramGenerator extends DefaultProcessDiagramGenerator {

    private static final String IMAGE_TYPE = "png";

    @Value("${flowable.activityFontName}")
    private String activityFontName;
    @Value("${flowable.labelFontName}")
    private String labelFontName;
    @Value("${flowable.annotationFontName}")
    private String annotationFontName;

    /**
     * 生成图片流
     *
     * @param bpmnModel             模型
     * @param highLightedActivities 活动节点
     * @param highLightedFlows      高亮线
     * @return
     */
    public InputStream generateDiagram(BpmnModel bpmnModel, List<String> highLightedActivities, List<String> highLightedFlows) {
        return generateDiagram(bpmnModel, IMAGE_TYPE, highLightedActivities,
                highLightedFlows, activityFontName, labelFontName, annotationFontName,
                null, 1.0, true);
    }

    /**
     * 生成图片流
     *
     * @param bpmnModel 模型
     * @return
     */
    public InputStream generateDiagram(BpmnModel bpmnModel) {
        return generateDiagram(bpmnModel, IMAGE_TYPE, activityFontName,
                labelFontName, annotationFontName,
                null, 1.0, true);
    }
}
