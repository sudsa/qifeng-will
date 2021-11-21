package com.hanxiaozhang.strategy;

import org.springframework.stereotype.Component;

@Component
public class AIFileStrategy implements IFileStrategy {
    @Override
    public FileTypeResolveEnum gainFileType() {
        return FileTypeResolveEnum.A;
    }

    @Override
    public void resolve(Object objectparam) {

    }
}
