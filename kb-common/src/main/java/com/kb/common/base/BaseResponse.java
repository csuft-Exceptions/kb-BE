package com.kb.common.base;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-14 - 19:20
 */
public class BaseResponse<T> {
        /**
         * 状态码
         */
        private Integer code;
        /**
         * 响应信息
         */
        private String msg;
        /**
         * 数据
         */
        private T data;
        /**
         *
         */
        private Integer total;

        private static final Integer SUCCESS = 200;
        private static final Integer FAILED  = 999;
        private static final Integer ERROR   = 1000;

        public BaseResponse(){}
        public  static <T> BaseResponse<T> success(T data){
                BaseResponse<T> baseResponse=new BaseResponse<>();
                baseResponse.setCode(SUCCESS);
                baseResponse.setMsg("成功");
                baseResponse.setData(data);

                return baseResponse;
        }

        public  static <T> BaseResponse<T> success(T data,Integer total){
                BaseResponse<T> baseResponse=new BaseResponse<>();
                baseResponse.setCode(SUCCESS);
                baseResponse.setMsg("成功");
                baseResponse.setData(data);
                baseResponse.setTotal(total);
                return baseResponse;
        }

        /**
         * 用户错误
         * @param data
         * @param <T>
         * @return
         */
        public static <T> BaseResponse<T> failed(T data){
                BaseResponse<T> baseResponse=new BaseResponse<>();
                baseResponse.setCode(FAILED);
                baseResponse.setMsg("失败");
                baseResponse.setData(data);
                return baseResponse;
        }

        /**
         * 系统错误
         * @param data
         * @param <T>
         * @return
         */
        public static <T> BaseResponse<T> error(T data){
                BaseResponse<T> baseResponse=new BaseResponse<>();
                baseResponse.setCode(ERROR);
                baseResponse.setMsg("失败,系统出现错误");
                baseResponse.setData(data);
                return baseResponse;
        }


        public Integer getCode() {
                return code;
        }

        public void setCode(Integer code) {
                this.code = code;
        }

        public String getMsg() {
                return msg;
        }

        public void setMsg(String msg) {
                this.msg = msg;
        }

        public T getData() {
                return data;
        }

        public void setData(T data) {
                this.data = data;
        }

        public Integer getTotal() {
                return total;
        }

        public void setTotal(Integer total) {
                this.total = total;
        }
}
