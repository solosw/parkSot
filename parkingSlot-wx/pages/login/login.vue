<template>
	<view>
		<view class="box-bg">
			<uni-nav-bar shadow title="信息认证" />
		</view>
		<view>
			<view class="avatar-container">
				<image :src="header" class="avatar" />
			</view>
		</view>
		<view style="text-align: center; margin-top: 30rpx;">
			<text>南宁民歌湖景区停车场服务小程序</text>
		</view>
		<button open-type="chooseAvatar" @chooseavatar="getAvtor" v-if="isHasToken==false">微信授权</button>
		<button @click="wxlogin" v-if="isHasToken">登陆</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				registerDate: {}, //存储用户数据用于用户注册微信
				header: "../../static/myinfo1.png",
				name: "",
				openid: "",
				isHasToken: false,
				isRegister: true,
				appid:"",
				secret:"",
			};
		},
		onShow() {
			const self=this;
			uni.request({
				url:self.systemRequest+"/getSystem",
				method:"POST",
				success: (res) => {
					self.appid=res.data.wxAppid
					self.secret=res.data.wxSecret
				}
			})
		},
		methods: {
			
			getAvtor(e) {
				this.header = e.detail.avatarUrl
				this.isHasToken = true
			},
			wxlogin() {
				if (!this.isHasToken) {
					uni.showToast({
						title: '请进行微信授权认证',
						icon: "none"

					});
					return
				}

				const self = this;
				uni.getUserProfile({
					desc: "获取头像和昵称",
					success: function(res) {
						uni.login({
							provider: 'weixin', //使用微信登录
							success: function(loginRes) {
								const appid = self.appid;
								const secrect = self.secret;
								const code = loginRes.code;
								const url = "https://api.weixin.qq.com/sns/jscode2session?appid=" +
									appid + "&secret=" + secrect + "&js_code=" + code +
									"&grant_type=authorization_code";
								uni.request({
									url: url,
									success: function(res) {
										self.openid = res.data.openid
										uni.request({
											url: self.request + "/login",
											method: "POST",
											data: {
												id: self.openid,
											
											},
											success: function(res) {

												var user = {
													id: res.data.id,
													name: res.data
														.name,
													token: res.data
														.token,
													header: self.header
												}

												uni.setStorage({
													key: 'user',
													data: user,
													success: function() {
														uni.switchTab({
															url: '/pages/parkinginfo/parkinginfo'
														});
													}
												});


											},

										})

									}
								})

							}
						});
					},

				})

			},



		}
	};
</script>

<style>
	.avatar-container {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.avatar {
		width: 100px;
		height: 100px;
		border-radius: 50%;
		/* 将图片显示为圆形 */
	}

	.myinput {
		background-color: #FFFFFF;
		height: 50rpx;
		padding: 15rpx;
		line-height: 50rpx;
		margin-top: 3rpx;


	}
</style>