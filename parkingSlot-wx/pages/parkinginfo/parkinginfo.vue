<template>
	<view>
		<view class="box-bg">
			<uni-nav-bar shadow title="停车场" />
		</view>
		<uni-card v-for="(item,index) in parkSlots" padding="0" spacing="0" :key="index">

			<template v-slot:cover>

				<view style="box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);">
					<uni-icons type="location" size="30">

					</uni-icons>
					{{item.address}}
				</view>


				<view class="custom-cover">
					<map style="width: 100%; height: 200rpx;" :latitude="item.lat" :longitude="item.lng"
						:markers="parkCovers[index]"></map>
				</view>
			</template>
			<uni-grid :column="3" :highlight="true">
				<uni-grid-item>
					<view class="grid-item-box" :style="{
				            'color': item.useNumber === item.slotNumber ? 'red' : (item.useNumber > item.slotNumber / 2 ? 'yellow' : 'green'),
				            'display': 'flex',
				            'flex-direction': 'column',
				            'justify-content': 'center',
				            'align-content': 'center',
				            'align-items': 'center',
				            'justify-items': 'center'
				        }">
						<view>{{ item.slotNumber }}</view>
						<text style="margin-top: 10%;" class="text">总车位</text>
					</view>
				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box" :style="{
				            'color': item.useNumber === item.slotNumber ? 'red' : (item.useNumber > item.slotNumber / 2 ? 'yellow' : 'green'),
				            'display': 'flex',
				            'flex-direction': 'column',
				            'justify-content': 'center',
				            'align-content': 'center',
				            'align-items': 'center',
				            'justify-items': 'center'
				        }">
						<view>{{ item.useNumber }}</view>
						<text style="margin-top: 10%;" class="text">占用车位</text>
					</view>
				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box" :style="{
				            'color': item.useNumber === item.slotNumber ? 'red' : (item.useNumber > item.slotNumber / 2 ? 'yellow' : 'green'),
				            'display': 'flex',
				            'flex-direction': 'column',
				            'justify-content': 'center',
				            'align-content': 'center',
				            'align-items': 'center',
				            'justify-items': 'center'
				        }">
						<view>{{ item.slotNumber - item.useNumber }}</view>
						<view class="text" style="margin-top: 10%;">剩余车位</view>
					</view>

				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box"
						style="background-color: #fff; display: flex;flex-direction: column;justify-content: center;align-content: center;align-items: center;justify-items: center;">
						<view>{{item.price}}元/小时</view>
						<text style="margin-top: 10%;" class="text">价格</text>
					</view>
				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box"
						style="background-color: #fff; display: flex;flex-direction: column;justify-content: center;align-content: center;align-items: center;justify-items: center;">
						<view>{{getDistance(item.lat, item
								.lng, currentlatitude, currentlongitude)}}米</view>
						<text style="margin-top: 10%;" class="text">距离</text>
					</view>
				</uni-grid-item>
			</uni-grid>
			<view slot="actions" class="card-actions no-border">
				<view class="card-actions-item" style="float: right;">
			
					<button  size="mini" class="card-actions-item-text" @click="denunciate(item)" type="default" style="background-color: #00aaff;margin-right: 10rpx;">举报</button>
					<button size="mini" class="card-actions-item-text" @click="appiont(item)" type="default" style="background-color: #00aaff;">预约</button>
				</view>
			</view>
		</uni-card>
		<view>
			<view>
				<!-- 普通弹窗 -->
				<uni-popup type="bottom" ref="popup" background-color="#fff" style="width: 400rpx;height: 300rpx;">
					<view class="popup-content">
						<uni-datetime-picker type="datetime" v-model="datetimesingle" style="height: 300rpx;"/>
						<uni-easyinput type="number" placeholder="预约时长(小时)" v-model="appointTime"></uni-easyinput>
					</view>
					<view style="display: flex; padding: 10rpx;">
						
						<button type="default" size="mini" @click="this.$refs.popup.close()"  style="background-color:#ff0000;"> 取消</button>
						<button size="mini" type="default"  @click="sureAppiont" style="background-color:#00aaff;" >确认</button>
					</view>
				</uni-popup>
			</view>
		</view>
		<uni-popup ref="inputDialog" type="dialog">
			<uni-popup-dialog ref="inputClose" mode="input" title="举报" placeholder="举报内容"
				@confirm="dialogInputConfirm"></uni-popup-dialog>
		</uni-popup>
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
					
					self.key=res.data.wxKey
					self.amapPlugin = new amap.AMapWX({
						key: self.key
					});
					self.init()
				}
			})
			
		},
		data() {
			return {
				appointTime: null,
				user: null,
				chooseSlot: null,
				datetimesingle: null,
				baseInfo: null,
				amapPlugin: null,
				key: '0f1b6537cf284b99f93fb6da7c6b323d',
				currentlatitude: 0,
				currentlongitude: 0,
				covers: [{
					latitude: 39.909,
					longitude: 116.39742,

				}, {
					latitude: 39.90,
					longitude: 116.39,

				}],
				parkSlots: [

				],

				parkCovers: [

				],
				parkDistance: [

				],
			}
		},
		methods: {
			dialogInputConfirm(value) {
				const self = this;
				uni.request({
					url: this.request + "/addDenunciation",
					data: {

						openId: self.user.id,
						slotId: self.chooseSlot.id,
						content: value
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {

							uni.showToast({
								title: "举报成功",
								icon: "success",

							})
						}
					}
				})
			},
			denunciate(item) {
				this.chooseSlot = item
				this.$refs.inputDialog.open();
			},
			sureAppiont() {
				var self = this;
				var selectedDateTime = new Date(self.datetimesingle).getTime(); // 获取选择的时间的时间戳
				var currentDateTime = new Date().getTime(); // 获取当前时间的时间戳
				var futureDateTime = currentDateTime + self.baseInfo.antecedentDay * 24 * 60 * 60 * 1000; // 计算7天后的时间戳
				if (self.user.status == 1) futureDateTime += 3 * 24 * 60 * 60 * 1000
				if (selectedDateTime > currentDateTime && selectedDateTime <= futureDateTime) {
					var price = self.appointTime * self.chooseSlot.price;
					if (self.user.status == 1) {
						price = price * self.baseInfo.vipDiscount
					}
					if (self.user.money < price) {
						uni.showToast({
							title: "余额不足",
							icon: "error"
						})
						return;
					}

					uni.request({
						url: self.request + "/appointSlot",
						data: {
							howLong: self.appointTime,
							time: self.datetimesingle,
							openId: self.user.id,
							slotId: self.chooseSlot.id,
							price: price,
							carSlotId: self.chooseSlot.useNumber + 1
						},
						method: "POST",
						success(res) {

							uni.showToast({
								title: "预约成功",
								icon: "success"
							})
							self.user = res.data
							self.chooseSlot.useNumber++;
						}
					})
					// 在这里执行您的逻辑
				} else {
					uni.showToast({
						title: "时间错误",
						icon:"error"
					
					})
				}
				this.$refs.popup.close()
			},
			appiont(item) {

				this.chooseSlot = item
				if (item.useNumber == item.slotNumber) {
					uni.showToast({
						title: "停车位已满",
						icon: "none"
					})
					return;
				}
				this.$refs.popup.open()
			},
			// 计算两个经纬度点之间的距离
			getDistance: function(lat1, lon1, lat2, lon2) {
				const R = 6371; // 地球半径，单位为公里
				const dLat = this.deg2rad(lat2 - lat1);
				const dLon = this.deg2rad(lon2 - lon1);

				const a =
					Math.sin(dLat / 2) * Math.sin(dLat / 2) +
					Math.cos(this.deg2rad(lat1)) * Math.cos(this.deg2rad(lat2)) *
					Math.sin(dLon / 2) * Math.sin(dLon / 2);
				const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				const distance = R * c; // 距离，单位为公里
				return Math.round(distance * 1000);
			},

			deg2rad: function(deg) {
				return deg * (Math.PI / 180);
			},
			init() {
				const self = this;
				this.amapPlugin.getRegeo({
					success: (data) => {

						self.currentlatitude = data[0].latitude
						self.currentlongitude = data[0].longitude
					},
					fail: (error) => {
						console.log(error)
					}
				});
				uni.request({
					url: self.systemRequest + "/getBaseInfo",
					method: "POST",
					success: function(res) {
						self.baseInfo = res.data
					}
				})
				uni.request({
					url: self.request + "/getSlots",
					method: "POST",
					success: function(res) {
						self.parkSlots = res.data
						for (var i = 0; i < self.parkSlots.length; i++) {
							var cover = [{
								latitude: self.parkSlots[i].lat,
								longitude: self.parkSlots[i].lng,
								iconPath: '../../static/location.png',
								id: i,
								width: 40,      
								height: 40,
							}]
							self.parkCovers.push(cover)
							self.parkDistance.push(self.getDistance(self.parkSlots[i].lat, self.parkSlots[i]
								.lng, self.currentlatitude, self.currentlongitude) * 1000)

						}
					}
				})
				uni.getStorage({
					key: "user",
					success(res) {
						uni.request({
							url: self.request + "/getMyinfo",
							data: {
								id: res.data.id
							},
							method: "POST",
							success: (response) => {

								self.user = response.data;



							},
							fail: () => {
								console.log("网络异常");
							}
						})
					}
				})

			}
		},
		onShow() {
			

		}
	}
</script>

<style>

</style>