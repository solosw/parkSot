<template>
	<view>
		<uni-nav-bar dark :fixed="true" shadow background-color="#007AFF" status-bar left-icon="left" left-text="返回"
			title="导航" @clickLeft="back()" />

		<view class="page-body">
			<view class="page-section page-section-gap">
				<map style="width: 100%; height: 400rpx;" :latitude="latitude" :longitude="longitude" :markers="covers"
					:polyline="polyline">
				</map>
			</view>
		</view>
		<view style="height: 200rpx;">
			<uni-list>
				<uni-list-item  v-if="index>=(currentPage-1)*5&&index<=currentPage*5-1"  v-for="(item,index) in descriptions" :title="item" :key="index"></uni-list-item>
			</uni-list>
			<uni-pagination :total="total" title="标题文字" :page-size="5" v-model="currentPage" @change="changePage" style="bottom: 0;"/>
		</view>
		
		
		
	</view>
</template>

<script>
	import amap, {
		AMapWX
	} from '../../common/amap-wx.130.js';
	export default {
		
		onLoad() {
			const self=this;
			uni.request({
				url:self.systemRequest+"/getSystem",
				success:function(res) {
					self.webkey=res.data.webKey
					self.wxkey=res.data.wxKey
					self.amapPlugin = new amap.AMapWX({
						key: self.wxkey
					});
					self.init()
				}
			})
			
			
			
			
			
		},
		data() {
			return {
				amapPlugin: null,
				webkey: "ac643cbba0181abb5d5f6a9c009ee45f",
				wxkey: "0f1b6537cf284b99f93fb6da7c6b323d",
				polyline: [{
					points: [],
					color: "#55ff7f",
					width: 6
				}, ],
				descriptions: [],
				latitude: null,
				longitude: null,
				covers: [],
				currentPage:1,
				total:1,
			}
		},
		methods: {
			changePage(e){
				
			},
			back() {
				uni.reLaunch({
					url: "/pages/parkinginfo/parkinginfo"
				})
			},
			init() {

				const self = this;
				self.amapPlugin.getRegeo({
					success: (data) => {

						const currentlatitude = data[0].latitude
						const currentlongitude = data[0].longitude

						uni.getStorage({
							key: "pos",
							success(res) {
								const customKey = self.webkey; // 替换为您的自定义密钥
								const customOrigin = currentlongitude + ',' + currentlatitude
								const customDestination = res.data; // 替换为您的自定义终点经纬度坐标

								self.covers = [{
										id: 1,
										latitude: res.data.split(",")[1],
										longitude: res.data.split(",")[0],
										width: 40,
										height: 40,
									},

									{
										id: 2,
										latitude: currentlatitude,
										longitude: currentlongitude,
										width: 40,
										height: 40,
									}

								]
								self.latitude = res.data.split(",")[1]
								self.longitude = res.data.split(",")[0]

								// 自定义参数值


								// 构建自定义URL
								const customUrl =
									'https://restapi.amap.com/v3/direction/driving?key=' + customKey +
									'&origin=' + customOrigin + '&destination=' + customDestination +
									'&originid=&destinationid=&extensions=base&strategy=&waypoints=&avoidpolygons=&avoidroad=';

								// 输出自定义URL


								uni.request({
									url: customUrl,
									success(resp) {
										var steps = resp.data.route.paths[0].steps
										var points = []
										// console.log(">",steps)
										for (let i = 0; i < steps.length; i++) {
											self.descriptions.push(steps[i].instruction)
											let polylines = steps[i].polyline.split(';')
											for (let j = 0; j < polylines.length; j++) {
												let locations = polylines[j].split(',');
												points.push({
													longitude: locations[0],
													latitude: locations[1],
												})
											}
										}

										self.polyline[0].points = points
										self.total=self.descriptions.length
									}
								})
							}
						})

					},
					fail: (error) => {
						console.log(error)
					}
				});

			}
		},
		onShow() {

			//this.init()


		}
	}
</script>

<style>

</style>