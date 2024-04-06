<template>
	<view>


		<uni-nav-bar dark :fixed="true" shadow background-color="#007AFF" status-bar left-icon="left" left-text="返回"
			title="我的订单" @clickLeft="back" />

		<view class="uni-padding-wrap uni-common-mt">
			<uni-segmented-control :current="current" :values="['未支付','已支付']" style-type="text"
				active-color="#007aff" @clickItem="onClickItem" />
		</view>
		<uni-card v-if="item.status==current+2" v-for="(item,index) in myRecords" padding="0" spacing="0" :key="index">

			<template v-slot:title>
				<view style="background-color: aqua;border: 1px solid whitesmoke; border-radius: 5rpx;">
					订单号:{{item.strId}}</view>
				<view style="background-color: greenyellow;border: 1px solid whitesmoke;border-radius: 5rpx;">
					停车时间：{{item.startTime}}</view>
				<view style="background-color:cornsilk;border: 1px solid whitesmoke;border-radius: 5rpx;">
					结束时间：{{item.endTime}}</view>
			</template>
			<template v-slot:cover>
				<view style="box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);">
					<uni-icons type="location" size="30">

					</uni-icons>
					{{address[index]}}
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
						<view>{{ item.howLong }}小时</view>
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
						<view>{{ getRealTime(item.startTime,item.endTime) }}小时</view>
						<view class="text" style="margin-top: 10%;">实际时长</view>
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
						<view>{{ realSlots[index].price }}元/小时</view>
						<text style="margin-top: 10%;" class="text">价格</text>
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
						<view>{{ realSlots[index].extraPrice }}元/小时</view>
						<text style="margin-top: 10%;" class="text">超时价格</text>
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
						<view>{{getRealTime(item.startTime,item.endTime)>item.howLong?"是":"否"}}</view>
						<text style="margin-top: 10%;" class="text">是否超时</text>
					</view>
				</uni-grid-item>

				<uni-grid-item>
					<view class="grid-item-box"
						style="background-color: #fff; display: flex;flex-direction: column;justify-content: center;align-content: center;align-items: center;justify-items: center;">
						<view>{{item.status==2?"未支付":"已支付"}}</view>
						<text style="margin-top: 10%;" class="text">订单状态</text>
					</view>
				</uni-grid-item>

			</uni-grid>
			<view slot="actions" class="card-actions no-border">
				<view class="card-actions-item" style="float: right;">
					<view style="font-size: large;font-weight: 600; padding: 10rpx;">
						<uni-icons type="cart" size="30"></uni-icons>
						{{getRealMoney(item.startTime,item.endTime,item.howLong,realSlots[index].price,realSlots[index].extraPrice)}}元
					</view>
					<button type="primary" v-if="item.status==2" size="mini" class="card-actions-item-text"
						@click="payOrder(item,getRealMoney(item.startTime,item.endTime,item.howLong,realSlots[index].price,realSlots[index].extraPrice))">支付</button>
					<button v-if="item.status==3" size="mini" class="card-actions-item-text" @click="deleteOrder(item)"
						type="warn">删除订单</button>
				</view>
			</view>
		</uni-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				current: 0,
				myRecords: [

				],
				user: null,
				Slots: [],
				address: [],
				realSlots: [],
			}
		},
		methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
				}
			},
			back() {
				uni.reLaunch({
					url: "/pages/parkinginfo/parkinginfo"
				})
			},
			getRealMoney(s, e, l, p, ep) {

				var realHours = this.getRealTime(s, e);
				if (realHours <= l) {
					return p * realHours
				} else {
					return l * realHours + (realHours - l) * ep
				}
			},

			getRealTime(s, e) {
				// 定义两个日期时间
				let startDate = new Date(s);
				let endDate = new Date(e); // 结束时间调整为14:45:00

				// 计算时间差（单位：毫秒）
				let timeDiff = endDate.getTime() - startDate.getTime();

				// 将毫秒转换为分钟
				let minutesDiff = timeDiff / (1000 * 60);

				// 如果时间差大于30分钟，则按照大于30分钟算作一小时的规则处理
				if (minutesDiff > 30) {
					minutesDiff = Math.ceil(minutesDiff / 60) * 60; // 向上取整到最接近的一小时
				}
				console.log(minutesDiff)
				// 将分钟转换为小时
				let hoursDiff = minutesDiff / 60;
				hoursDiff = Math.floor(hoursDiff);
				if (hoursDiff < 1) hoursDiff = 1
				return hoursDiff; // 输出：24（相差24小时）

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
			payOrder(item, cost) {



				if (cost > this.user.money) {
					uni.showToast({
						title: "余额不足",
						icon: "fail"
					})
					return;
				}
				const self = this;
				uni.request({
					url: self.request + "/payOrder",
					data: {
						id: item.strId,
						openId: self.user.id,
						price: cost
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {
							uni.showToast({
								title: "支付成功"
							})
							self.user = res.data
							item.status = 3
						}
					}
				})

			},
			deleteOrder(item) {
				const self = this;
				uni.request({
					url: self.request + "/deleteOrder",
					data: {
						id: item.strId,
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {
							uni.showToast({
								title: "删除成功!",
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
						url: self.request + "/getMyOrder",
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

												self.address.push(self.Slots[j]
													.address)
												self.realSlots.push(self.Slots[j])
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