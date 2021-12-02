package com.qifeng.will.thred.call;

import io.swagger.models.auth.In;
import lombok.Data;
import org.elasticsearch.common.util.concurrent.AbstractAsyncTask;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.*;

@Data
public class WorkOrder implements Serializable {

    private String projectCode;

    private String projectName;

    private String rate;


}
