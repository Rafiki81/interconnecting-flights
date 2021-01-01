package com.rafiki.interconnectingflights.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
@ApiModel
public class Interconnection {
	@ApiModelProperty(notes = "Number of Stops",name="stops",required=true)
    Integer stops;
	@ApiModelProperty(notes = "List of legs",name="legs",required=true)
    List<Leg> legs;
}
