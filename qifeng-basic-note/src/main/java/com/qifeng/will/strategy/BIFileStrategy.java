package com.qifeng.will.strategy;

import org.springframework.stereotype.Component;

@Component
public class BIFileStrategy implements IFileStrategy{
    @Override
    public FileTypeResolveEnum gainFileType() {
        return FileTypeResolveEnum.B;
    }

    @Override
    public void resolve(Object objectparam) {

    }
}
