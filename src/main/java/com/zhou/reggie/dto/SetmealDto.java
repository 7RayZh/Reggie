package com.zhou.reggie.dto;

import com.zhou.reggie.entity.Setmeal;
import com.zhou.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
