package react.freddy.com.ubid.data

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
const val DATABASE_NAME = "ubid-db"

//base url整理 整个app目前调用了三个后台 基地址应该只有三个 其他的url都是再该url里面扩展而来
//1.EFS系统
const val BASE_EFS_URL_DEV = "https://efsdev.saic-finance.com/"
const val BASE_EFS_URL_SIT = "https://efssit.saic-finance.com/"
const val BASE_EFS_URL_UAT = "https://efsu.saic-finance.com/"
const val BASE_EFS_URL_PRO = "https://efsmobile.saicfinance.com/"
var BASE_EFS_URL = BASE_EFS_URL_SIT

const val BASE_ITMGR_URL = "https://itmgr.saicfinance.com/"