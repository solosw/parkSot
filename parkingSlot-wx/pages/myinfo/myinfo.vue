<template>
	<view>
		<view class="box-bg">
			<uni-nav-bar shadow title="个人信息" />
		</view>
		<view class="myinfobox">
			<image :src="header" style="width: 150rpx; height: 150rpx; margin-left: 20rpx;border-radius: 50%;">
			</image>
			<view style="height: 70%; display: flex; flex-direction: column; margin-right: 200rpx; font-size: small;">
				<text>昵称：{{user.name==null?"无":user.name}}</text>
				<text>手机：{{user.phone}}</text>
				<text>车牌：{{user.carCard}}</text>
				<text>车名：{{user.carKind}}</text>
				<text>余额：{{money}}元</text>
				<text>是否为vip用户:{{status==1?'是':'否'}}</text>
			</view>

		</view>

		<view style="background-color: #ffffff;text-align: center; margin: 30rpx; padding: 10rpx; border-radius: 5rpx;"
			@click="changenickname()">
			<text>充值</text>
		</view>
		<view style="background-color:  #ffffff;text-align: center; margin: 30rpx; padding: 10rpx; border-radius: 5rpx;"
			@click="this.$refs.showRight.open()">
			<text>修改信息</text>
		</view>

		<view style="background-color:  #ffffff;text-align: center; margin: 30rpx; padding: 10rpx; border-radius: 5rpx;"
			@click="this.$refs.feedback.open()">
			<text>反馈</text>
		</view>

		<view v-if="status!=1" style="background-color: #ffffff;text-align: center; margin: 30rpx; padding: 10rpx; border-radius: 5rpx;"
			@click="changeVip()">
			<text>充值成为vip</text>
		</view>
		<view style="background-color:  #ffffff;text-align: center; margin: 30rpx; padding: 10rpx;border-radius: 5rpx;" @click="toMyOrder">
			<text>我的订单</text>
		</view>
		<view>
			<!-- 输入框示例 -->
			<uni-popup ref="inputDialog" type="dialog">
				<uni-popup-dialog ref="inputClose" mode="input" title="充值" placeholder="金额"
					@confirm="dialogInputConfirm"></uni-popup-dialog>
			</uni-popup>
		</view>
		<view>
			<!-- 输入框示例 -->
			<uni-popup ref="feedback" type="dialog">
				<uni-popup-dialog ref="inputClose" mode="input" title="反馈" placeholder="反馈内容"
					@confirm="feedbackConfirm"></uni-popup-dialog>
			</uni-popup>
		</view>
		<uni-drawer ref="showRight" mode="right" :mask-click="false">
			<scroll-view style="height: 100%;" scroll-y="true">
				<uni-forms :modelValue="formData">
					<uni-forms-item required label="昵称" name="name">
						<uni-easyinput type="text" v-model="formData.name" placeholder="昵称" />
					</uni-forms-item>
					<uni-forms-item required label="phone" name="手机号">
						<uni-easyinput type="text" v-model="formData.phone" placeholder="手机号" />
					</uni-forms-item>
					<uni-forms-item required name="carCard" label="车牌">
						<uni-easyinput type="text" v-model="formData.carCard" placeholder="车牌" />
					</uni-forms-item>
					<uni-forms-item required name="carKind" label="车名">
						<uni-easyinput type="text" v-model="formData.carKind" placeholder="车名" />
					</uni-forms-item>
				</uni-forms>

				<view style="display: flex;justify-content: center;align-items: center;align-content: center;">
					<button @click="this.$refs.showRight.close()" type="primary" size="mini">取消</button>
					<button type="primary" @click="updateInfo" size="mini">确认</button>
				</view>
			</scroll-view>
		</uni-drawer>
		<view style="background-color:  #ffffff;text-align: center; margin: 30rpx; padding: 10rpx; border-radius: 5rpx;" @click="logout()">
			<text>注销登录</text>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				money: 0,
				status: 0,
				platenumber: "",
				balance: "",
				user: null,
				openid: "",
				header: "",
				formData: {
					name: "",
					carKind: "",
					carCard: "",
					phone: "",
				}
			}
		},
		onShow() {

			const self = this;
			uni.getStorage({
				key: "user",
				success: function(res) {
					self.user = res.data
					self.header = self.user.header
					self.openid = self.user.id
					self.getMyinfo();
				}
			})
		},
		methods: {
			toMyOrder() {
				uni.reLaunch({
					url: "/pages/order/order"
				});
			},
			feedbackConfirm(value) {
				const self = this;
				uni.request({
					url: self.request + "/addFeedback",
					data: {
						content: value,
						openId: self.openid
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {

							uni.showToast({
								title: "反馈成功",
								icon: "none"

							})
						}
					}
				})

				this.$refs.feedback.close()
			},
			changeVip() {
				const self = this;
				uni.request({
					url: this.request + "/updateInfo",
					data: {
						status: 1,
						id: self.openid
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {
							self.status = 1
							uni.showToast({
								title: "升级成功",
								icon: "success",

							})
						}
					}
				})
			},
			dialogInputConfirm(value) {
				const self = this;
				uni.request({
					url: this.request + "/updateInfo",
					data: {
						money: Number.parseFloat(self.money) + Number.parseFloat(value),
						id: self.openid
					},
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {
							self.money += Number.parseFloat(value)
							uni.showToast({
								title: "充值成功",
								icon: "success",

							})
						}
					}
				})
			},
			updateInfo() {
				const self = this;
				const data = {
					id: self.user.id,
					name: self.formData.name,
					phone: self.formData.phone,
					carKind: self.formData.carKind,
					carCard: self.formData.carCard
				}
				const allValuesPresent = Object.values(data).every(value => value !== '' && value !== null && value !== undefined);
				if(!allValuesPresent){
					uni.showToast({
						title:"字段不能为空",
						icon:"error"
					})
					return;
				}
				uni.request({
					url: this.request + "/updateInfo",
					data: data,
					method: "POST",
					success: function(res) {
						if (res.statusCode == 200) {
							console.log(res.data)
							self.user = self.formData
							uni.showToast({
								title: "修改成功",
								icon: "success",
								success() {
									self.$refs.showRight.close()
								}
							})
						}
					}
				})
			},

			changenickname() {
				console.log("你点击了修改昵称");
				this.$refs.inputDialog.open();
			},

			logout() {
				uni.showModal({
					title: '提示',
					content: '你确实要注销吗？',
					success: function(res) {
						if (res.confirm) {
							console.log('用户点击确定');
							uni.clearStorageSync()
							uni.redirectTo({
								url: '/pages/login/login'
							})
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			},

			getMyinfo() {
				const self = this;
				uni.request({
					url: self.request + "/getMyinfo",
					data: {
						id: self.user.id
					},
					method: "POST",
					success: (res) => {

						self.user = res.data;
						self.money = res.data.money
						self.status = res.data.status


					},
					fail: () => {
						console.log("网络异常");
					}
				})
			}
		}
	}
</script>

<style>
	.myinfobox {
		background-color: #FFFFFF;
		margin: 30rpx;
		height: 400rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
	}
</style>