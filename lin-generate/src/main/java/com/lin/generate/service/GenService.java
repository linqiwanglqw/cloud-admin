package com.lin.generate.service;

import java.util.List;
import java.util.Map;

public interface GenService {
    List<Map> list(String name);

    byte[] code(String tables);
}
