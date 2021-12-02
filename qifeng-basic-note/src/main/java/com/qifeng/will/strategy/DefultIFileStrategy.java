package com.qifeng.will.strategy;

import org.springframework.stereotype.Component;

@Component
public class DefultIFileStrategy implements IFileStrategy{
    @Override
    public FileTypeResolveEnum gainFileType() {
        return FileTypeResolveEnum.D;
    }

    @Override
    public void resolve(Object objectparam) {

    }
}
