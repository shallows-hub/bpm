# bpm
1、注意修改数据库里，admin的alias：sys_properties, admin.account 的 value为对应的admin的account

接口记录
1. post http://localhost:8080/bpm/definition/listJson
获取所有流程
2. post bpm/instance/doAction
{"defId":"420146009683787777","instanceId":"","formType":"INNER","data":null,"action":"start","nodeId":""}