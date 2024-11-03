import request from '@/utils/request'

export function getWordPageList(data) {
    return request({
    url: '/Word/getWordPageList',
    method: 'post',
    data
})
}