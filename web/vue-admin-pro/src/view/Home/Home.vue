<template>
  <section>


    <section class="search-panel">

      <Row :gutter="16">
        <Col span="12">
          <Card style="height: 223px;">
            <p slot="title">全文搜索</p>
            <Input search enter-button placeholder="请输入关键字" v-model="formFullKeyword" @on-search="handleFormFull" />
          </Card>
        </Col>
        <Col span="12">
          <Card>
            <p slot="title">高级搜索</p>
            <Row :gutter="16">
              <Col span="8">
                <Input v-model="formAdvanced.name" placeholder="名称"></Input>
              </Col>
              <Col span="8">
                <Input v-model="formAdvanced.ad" placeholder="广告"></Input>
              </Col>
              <Col span="4">
                <Input v-model="formAdvanced.priceMin" placeholder="最低价"></Input>
              </Col>
              <Col span="4">
                <Input v-model="formAdvanced.priceMax" placeholder="最高价"></Input>
              </Col>
            </Row>
            <br>
            <Row :gutter="16">
              <Col span="8">
                <Input v-model="formAdvanced.memory" placeholder="内存"></Input>
              </Col>
              <Col span="8">
                <Input v-model="formAdvanced.storage" placeholder="存储"></Input>
              </Col>
              <Col span="8">
                <Input v-model="formAdvanced.screen" placeholder="屏幕"></Input>
              </Col>
            </Row>
            <br>
            <Row :gutter="16">
              <Col span="16">
                <DatePicker v-model="formAdvanced.time" type="datetimerange" placeholder="开始时间 ~ 结束时间" style="width: 100%"></DatePicker>
              </Col>
              <Col span="8">
                <Button type="primary" @click="handleFormSubmit">搜索</Button>
              </Col>
            </Row>
          </Card>
        </Col>
      </Row>

    </section>

    <section class="search-info-panel">
      找到相关结果约 {{ searchInfo.resultNum }} 个（{{ searchInfo.time }}秒）
    </section>

    <div class="data-container">
      <Table border :columns="columns" :data="dataList" :loading="tableLoading" stripe>
        <template slot-scope="{ row }" slot="img">
          <img :src="row.imgUrl" alt="" class="img">
        </template>

        <template slot-scope="{ row }" slot="price">
          <span class="price">￥<span class="number">{{ row.price }}</span></span>
        </template>
      </Table>
    </div>

    <div class="page-container">
      <Page :total="page.totalElements" :current="page.currentPage" @on-change="handlePageGetData" :page-size="page.pageSize"/>
    </div>
  </section>
</template>

<script>
  import { apiFullSearch, apiAdvancedSearch } from "@/api/ApiData";

  export default {
    name: 'Home',
    data() {
      return {
        res: '',
        form: {},
        apiResponse: {
          "message": "Success",
          "success": true,
          "data": [
            {
              "id": "8z1PL3MB3aBfAxkrq0l2",
              "name": "飞利浦E212A 翻盖按键超长待机 移动版双卡双待老人手机 学生备用功能机 飞利浦手机 深锖色（深蓝色）",
              "ad": "咨询客服-领取优惠-下单立省-现货速发，15天价保，买贵补差价",
              "price": 268,
              "imgUrl": "//img10.360buyimg.com/n7/jfs/t1/24002/31/1438/337790/5c120bbeE6b9e6e12/befe00624e9a62a2.jpg",
              "memory": "2GB以下运存",
              "storage": "8GB以下",
              "screen": "2.8英寸",
              "createTimeStamp": 1594226617204,
              "createTimeString": "2020-07-09 00:43:37,204"
            },
            {
              "id": "7j1PL3MB3aBfAxkrf0nz",
              "name": "荣耀Play3 6.39英寸魅眼全视屏 4000mAh大电池 真4800万AI三摄 畅玩全网通 幻夜黑 全网通6G+64G",
              "ad": "荣耀Play3 6.39英寸魅眼全视屏 4000mAh大电池 真4800万AI三摄 畅玩全网通 幻夜黑 全网通6G+64G",
              "price": 999,
              "imgUrl": "//img13.360buyimg.com/n7/jfs/t1/126334/20/1159/208111/5eba565cE6065f0bc/4184f6b681990d1d.png",
              "memory": "6GB运存",
              "storage": "64GB",
              "screen": "6.39 英寸",
              "createTimeStamp": 1594226606065,
              "createTimeString": "2020-07-09 00:43:26,065"
            },
            {
              "id": "8j1PL3MB3aBfAxkrq0lp",
              "name": "小米 Redmi 红米K30 极速版 5G手机 深海微光 6GB+128GB",
              "ad": "5G双模，120Hz流速屏，骁龙768G，前置挖孔双摄，索尼6400万后置四摄，30W快充！K30i新品",
              "price": 1599,
              "imgUrl": "//img12.360buyimg.com/n7/jfs/t1/111819/3/6275/81916/5eba1a81Ee5a49643/cb43bd5ca9139ded.jpg",
              "memory": "6GB运存",
              "storage": "128GB",
              "screen": "6.67英寸",
              "createTimeStamp": 1594226617192,
              "createTimeString": "2020-07-09 00:43:37,192"
            },
            {
              "id": "9z1PL3MB3aBfAxkrq0mp",
              "name": "【京东/顺丰物流】小米（MI） 红米 10X Pro 手机 畅爽芯 5G 4800万四摄 水滴全面屏 深海蓝 8GB+256GB",
              "ad": "天玑820旗舰芯，流光四摄，畅快自然！",
              "price": 2499.6,
              "imgUrl": "//img11.360buyimg.com/n7/jfs/t1/126871/31/4471/68731/5ee0aed0E95272afc/874ff8e92a373fd8.jpg",
              "memory": "8GB运存",
              "storage": "256GB",
              "screen": "6.57英寸",
              "createTimeStamp": 1594226617256,
              "createTimeString": "2020-07-09 00:43:37,256"
            },
            {
              "id": "9T1PL3MB3aBfAxkrq0mP",
              "name": "华为 HUAWEI nova 6（5G/4G）手机 前置广角双摄 超感光 麒麟990芯片 苏音蓝 4G版全网通(8GB+128GB)",
              "ad": "华为直供货源，现货发售，麒麟990",
              "price": 2499,
              "imgUrl": "//img12.360buyimg.com/n7/jfs/t1/95710/36/8488/112371/5e060761Ebe828725/090b6c2685b7e2c5.jpg",
              "memory": "8GB运存",
              "storage": "128GB",
              "screen": "6.57英寸",
              "createTimeStamp": 1594226617230,
              "createTimeString": "2020-07-09 00:43:37,230"
            },
            {
              "id": "9D1PL3MB3aBfAxkrq0mC",
              "name": "华为（HUAWEI） 华为Mate20 手机 麒麟980AI智能芯片全面屏超微距影像超大广角 亮黑色 全网通6GB+64GB",
              "ad": "6.53英寸，4000毫安电池",
              "price": 2499,
              "imgUrl": "//img11.360buyimg.com/n7/jfs/t1/117715/37/5325/719692/5eb385f4E0c52654d/d0216717c5caf7c8.png",
              "memory": "6GB运存",
              "storage": "64GB",
              "screen": "6.53英寸",
              "createTimeStamp": 1594226617217,
              "createTimeString": "2020-07-09 00:43:37,217"
            },
            {
              "id": "8D1PL3MB3aBfAxkrq0lN",
              "name": "华为荣耀畅玩8A 智能老人手机 6.09英寸珍珠全面屏 震撼大音量4G全面屏手机 双卡双待 极光蓝 全网通3G+32G",
              "ad": "2+1三卡槽，前置800万美颜相机",
              "price": 759,
              "imgUrl": "//img11.360buyimg.com/n7/jfs/t1/115627/5/5325/774214/5eb38609Ea16ee95a/5ade71eb6ec40a45.png",
              "memory": "3GB运存",
              "storage": null,
              "screen": "6.09英寸",
              "createTimeStamp": 1594226617164,
              "createTimeString": "2020-07-09 00:43:37,164"
            },
            {
              "id": "8T1PL3MB3aBfAxkrq0lZ",
              "name": "三星Galaxy Note10+ 5G手机 密斯白 全网通（12+256G）",
              "ad": "三星授权，现货速发，骁龙855处理器，4300毫安大电池，支持无线充电，NFC三星s20+促销优惠",
              "price": 5849,
              "imgUrl": "//img10.360buyimg.com/n7/jfs/t1/142890/22/1570/168857/5ef723f7E96abf240/0ac10b780550137a.jpg",
              "memory": "12GB运存",
              "storage": "256GB",
              "screen": null,
              "createTimeStamp": 1594226617176,
              "createTimeString": "2020-07-09 00:43:37,176"
            },
            {
              "id": "7z1PL3MB3aBfAxkrq0lA",
              "name": "华为（HUAWEI） Mate20RS 保时捷版手机 全网通8G+512G 玄黑",
              "ad": "高端机器，非质量问题不支持7无理由退货，不支持保价！",
              "price": 7399,
              "imgUrl": "//img10.360buyimg.com/n7/jfs/t1/98542/27/15296/115574/5e6f473aE9fec30f2/98ce186f066b111d.jpg",
              "memory": "8GB运存",
              "storage": "512GB",
              "screen": null,
              "createTimeStamp": 1594226617151,
              "createTimeString": "2020-07-09 00:43:37,151"
            },
            {
              "id": "9j1PL3MB3aBfAxkrq0mc",
              "name": "摩托罗拉 Motorola z3（XT1929-15）全面屏模块化全网通4G手机 双卡双待 星钻黑6GB+128GB",
              "ad": "12期免息0首付下单减300摩托罗拉G7plus火热抢购中",
              "price": 3588,
              "imgUrl": "//img12.360buyimg.com/n7/jfs/t1/124937/8/1134/373994/5eba14e9Ef18445fa/359efe8e16ace971.jpg",
              "memory": null,
              "storage": null,
              "screen": null,
              "createTimeStamp": 1594226617243,
              "createTimeString": "2020-07-09 00:43:37,243"
            }
          ],
          "page": {
            "totalElements": 10000,
            "totalPages": 1000,
            "pageSize": 10,
            "currentPage": 1
          }
        },
        dataList: [],
        tableLoading: false,
        page: {
          currentPage: 1
        },
        resultNum: 10000,
        searchInfo: {
          resultNum: 0,
          time: 0
        },
        advancedSearchMode: false,
        columns: [
          {
            title: 'ID',
            key: 'id',
            width: '200'
          },
          {
            title: '图片',
            slot: 'img',
            align: "center"
          },
          {
            title: '标题',
            key: 'name'
          },
          {
            title: '价格',
            slot: 'price',
            align: "center",
            width: '150'
          },
          {
            title: '广告',
            key: 'ad'
          },
          {
            title: '内存',
            key: 'memory',
            width: '100'
          },
          {
            title: '存储',
            key: 'storage',
            width: '100'
          },
          {
            title: '屏幕',
            key: 'screen',
            width: '100'
          },
          {
            title: '创建时间',
            key: 'createTimeString',
            width: '200'
          }
        ],
        param: {},
        formFullKeyword: '',
        formAdvanced: {
          name: '',
          ad: '',
          priceMin: '',
          priceMax: '',
          memory: '',
          storage: '',
          screen: '',
          time: []
        },
      }
    },
    methods: {
      getData() {
        this.getFullSearch()
      },
      getFullSearch() {
        this.advancedSearchMode = false
        let param = {
          keyword: this.formFullKeyword,
          currentPage: this.page.currentPage
        }
        apiFullSearch(param).then(result => {
          if (result.success) {
            this.dataList = result.data.content
            this.page = result.page
            this.searchInfo.resultNum = this.page.totalElements
            this.searchInfo.time = result.data.time
          }
        })
      },
      getAdvancedSearch() {
        this.advancedSearchMode = true
        let param = {
          currentPage: this.page.currentPage,
          name: this.formAdvanced.name,
          ad: this.formAdvanced.ad,
          priceMin: this.formAdvanced.priceMin,
          priceMax: this.formAdvanced.priceMax,
          memory: this.formAdvanced.memory,
          storage: this.formAdvanced.storage,
          screen: this.formAdvanced.screen,
          startTimeStamp: this.formAdvanced.time[0] === '' ? '' : this.formAdvanced.time[0].getTime(),
          endTimeStamp: this.formAdvanced.time[1] === '' ? '' : this.formAdvanced.time[1].getTime()
        }
        apiAdvancedSearch(param).then(result => {
          if (result.success) {
            this.dataList = result.data.content
            this.page = result.page
            this.searchInfo.resultNum = this.page.totalElements
            this.searchInfo.time = result.data.time
          }
        })
      },
      handlePageGetData(val) {
        this.page.currentPage = val;
        if (this.advancedSearchMode) {
          this.getAdvancedSearch()
        } else {
          this.getFullSearch()
        }
      },
      handleFormSubmit() {
        this.getAdvancedSearch()
      },
      handleFormFull() {
        this.getFullSearch()
      }
    },
    mounted() {
      this.getData()
    }
  }
</script>

<style scoped lang="stylus">

  .search-info-panel
    margin-top 30px
    color #999
    font-size 10px

  .data-container
    margin-top 10px

  .img
    width 150px

  .price
    color #e01222

    .number
      font-size 24px

  .page-container
    display flex
    justify-content flex-end
    margin-top 30px
</style>
