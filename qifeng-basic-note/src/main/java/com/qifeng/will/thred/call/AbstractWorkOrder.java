package com.qifeng.will.thred.call;

import java.util.Map;
import java.util.concurrent.Callable;

public abstract class AbstractWorkOrder implements Callable<WorkOrder> {

    public String index;

    public Map<String, Object> reqMap;

    public String projectCode;

}
