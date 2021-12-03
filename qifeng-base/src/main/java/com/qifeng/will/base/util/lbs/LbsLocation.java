package com.bpaas.doc.framework.base.util.lbs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class LbsLocation implements Serializable {

  private static final long serialVersionUID = -1065059350323640794L;
  /**
   * 经度
   */
  private double lng;
  /**
   * 纬度
   */
  private double lat;
}
