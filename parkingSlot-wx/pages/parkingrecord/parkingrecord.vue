<template>
	<view>
		<view class="box-bg">
			<uni-nav-bar shadow title="预约记录" />
		</view>
		<uni-card v-if="myRecords.length>0" v-for="(item,index) in myRecords" padding="0" spacing="0" :key="index">
			<template v-slot:title>
				<view style="background-color: aqua;border: 1px solid whitesmoke; border-radius: 5rpx;">
					订单号:{{item.strId}}</view>
				<view style="background-color: greenyellow;border: 1px solid whitesmoke;border-radius: 5rpx;">
					预约时间:{{item.time }}</view>
			</template>
			<template v-slot:cover>
				<view style="box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);">
					<uni-icons type="location" size="30">

					</uni-icons>
					{{address[index]}}
				</view>
				<view class="custom-cover">
					<map style="width: 100%; height: 200rpx;" :latitude="covers[index][0].latitude"
						:longitude="covers[index][0].longitude" :markers="covers[index]"></map>
				</view>
			</template>
			<uni-grid :column="4" :highlight="true">

				<uni-grid-item>
					<view class="grid-item-box" :style="{
				            'display': 'flex',
				            'flex-direction': 'column',
				            'justify-content': 'center',
				            'align-content': 'center',
				            'align-items': 'center',
				            'justify-items': 'center'
				        }">
						<view>{{ item.howLong }}</view>
						<text style="margin-top: 10%;" class="text">预计时长</text>
					</view>
				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box" :style="{
				           
				            'display': 'flex',
				            'flex-direction': 'column',
				            'justify-content': 'center',
				            'align-content': 'center',
				            'align-items': 'center',
				            'justify-items': 'center'
				        }">
						<view>{{ item.carSlotId }}</view>
						<view class="text" style="margin-top: 10%;">车位编号</view>
					</view>

				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box"
						style="background-color: #fff; display: flex;flex-direction: column;justify-content: center;align-content: center;align-items: center;justify-items: center;">
						<view>{{item.price}}元</view>
						<text style="margin-top: 10%;" class="text">价格</text>
					</view>
				</uni-grid-item>
				<uni-grid-item>
					<view class="grid-item-box"
						style="background-color: #fff; display: flex;flex-direction: column;justify-content: center;align-content: center;align-items: center;justify-items: center;">
						<view>{{item.status==0?"未停":"已停"}}</view>
						<text style="margin-top: 10%;" class="text">停车状态</text>
					</view>
				</uni-grid-item>

			</uni-grid>
			<view slot="actions" class="card-actions no-border">
				<view class="card-actions-item" style="float: right;">
					<button type="default" style="background-color:#ff0000;margin-left:10rpx"  v-if="item.status==0" size="mini" class="card-actions-item-text"
						@click="cancelAppoint(item)">取消预约</button>
					<button  v-if="item.status==0" size="mini" class="card-actions-item-text"
						@click="startNav(index)" type="default" style="background-color:#00ff00;margin-left:10rpx">开始导航</button>
					<button v-if="item.status==0" size="mini" class="card-actions-item-text"
						@click="startAppoint(item)" style="margin-left:10rpx">开始停车</button>
					<button v-if="item.status==1" size="mini" class="card-actions-item-text"
						@click="endAppoint(item)" style="background-color:#ff0000;margin-left:10rpx">结束停车</button>
				</view>
			</view>
		</uni-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				myRecords: [

				],
				user: null,
				covers: [

				],
				Slots: [],
				address: [],
				
			}
		},
		methods: {
			startNav(index) {
				
				const data=''+this.covers[index][0].longitude+','+this.covers[index][0].latitude
				uni.setStorage({
					key:"pos",
					data:data,
					success() {
						uni.reLaunch({
							url:"/pages/Nav/Nav"
						})	
					}
				})
				
					
			},
			cancelAppoint(item) {
				const self = this;
				console.log(item)
				uni.request({
					url: self.request + "/cancelAppoint",
					data: {
						id: item.strId
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {
							uni.showToast({
								title: "取消成功",
								success() {
									uni.redirectTo({
										url: "/pages/myinfo/myinfo"
									})
								}
							})


						}
					}
				})
			},
			startAppoint(item) {
				const self = this;
				uni.request({
					url: self.request + "/startAppoint",
					data: {
						id: item.strId
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {
							uni.showToast({
								title: "开始停车"
							})
							item.status = 1
						}
					}
				})

			},
			endAppoint(item) {
				const self = this;
				uni.request({
					url: self.request + "/endAppoint",
					data: {
						id: item.strId,
						slotId: item.slotId
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {
							uni.showToast({
								title: "请及时支付!",
								success() {
									uni.redirectTo({
										url: "/pages/order/order"
									})
								}
							})


						}
					}
				})

			}
		},

		onShow() {
			const self = this;
			uni.getStorage({
				key: "user",
				success: function(res) {
					self.user = res.data
					uni.request({
						url: self.request + "/getMyRecords",
						data: {
							openId: self.user.id
						},
						method: "POST",
						success: function(res1) {
							self.myRecords = res1.data
							console.log(self.myRecords)
							uni.request({
								url: self.request + "/getSlots",
								method: "POST",
								success: function(res2) {
									self.Slots = res2.data
									for (var i = 0; i < self.myRecords.length; i++) {
										for (var j = 0; j < self.Slots
											.length; j++) {
											if (self.Slots[j].id == self.myRecords[i]
												.slotId) {
												var cover = [{
													latitude: self.Slots[j]
														.lat,
													longitude: self.Slots[j]
														.lng,
													iconPath: '../../static/location.png',
													id: i * 100 + j,
													width: 40,
													height: 40,
												}]
												self.address.push(self.Slots[j]
													.address)
												self.covers.push(cover)
												break;
											}
										}
									}
								}
							})
						}
					})


				}
			})
		}
	}
</script>

<style>

</style>