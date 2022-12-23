import request from '@/utils/request'


//获取菜单信息
export function generateList(data) {
    return request({
        url: '/generate/list',
        method: 'get',
        params:data,
    })
}

export function generateCode(data) {
    return request({
        url: '/generate/code',
        method: 'get',
        responseType: 'blob', //必须在接口中配置 responseType 属性.
        headers:{ 'Content-Type': 'application/json; application/octet-stream'},
        params:data,
    })
}