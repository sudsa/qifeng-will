package com.qifeng.will.base.exception;


public class BpaasException extends Exception{

        /**
         * @Fields serialVersionUID : TODO
         */
        private static final long serialVersionUID = 1L;

        public BpaasException(){
            super();
        }

        public BpaasException(String msg){
            super(msg);
        }

        public BpaasException(Throwable throwable){
            super(throwable);
        }

        public BpaasException(String msg, Throwable throwable){
            super(msg, throwable);
        }
}
