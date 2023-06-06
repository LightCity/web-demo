package me.longli.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FastJsonTest {

    public static void main(String[] args) {
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            list.add(new Item(UUID.randomUUID().toString(), i));
        }
        Con con = new Con("test", list);
        String str = JSON.toJSONString(con);

        String str2 = "[{\"xxoo\": \"https://img.souche.com/xxoo.jpg\"}]";
        List<?> list1 = JSON.parseArray(str2, (Class) null);
        System.out.println(list1);
        // Con con1 = JSON.parseObject(str2, Con.class);
        // System.out.println(con1.getList().get(0).getClass().getName());
    }
}

@Data
@AllArgsConstructor
class Item {
    private String name;

    private int id;
}

@Data
@AllArgsConstructor
class Con {
    private String title;

    private List<Item> list;
}
