<template>
	<view>
		<view class="box-bg">
			<uni-nav-bar shadow title="我的消息" />
		</view>
		<uni-list :border="true">
			<view v-for="(item,index) in messages" :key="index">
				<uni-list-chat clickable v-if="item.status==0" :avatar-circle="true" title="管理员"
					avatar="/static/manager.png" note="未读" :time="item.time" badge-text="dot"
					@click="lookContent(item)">
				</uni-list-chat>
				<uni-list-chat v-else clickable :avatar-circle="true" title="管理员" avatar="/static/manager.png" note="已读"
					:time="item.time" @click="lookContent(item)">
				</uni-list-chat>
			</view>



		</uni-list>

		<uni-popup ref="alertDialog" type="dialog">
			<uni-popup-dialog type="success" cancelText="删除" confirmText="已读" title="内容" :content="content"
				@close="deleteMessage()" ></uni-popup-dialog>
		</uni-popup>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: null,
				messages: [],
				content: "",
				chooseItem:null
			}
		},
		methods: {
			deleteMessage(item){
				const self=this;
				uni.request({
					url: self.request + "/deleteMessage",
					data: {
						id:self.chooseItem.id
					},
					method: "POST",
					success: function(res) {
						uni.redirectTo({
							url:"/pages/message/message"
						})
					}
				})
			},
			lookContent(item) {
				this.chooseItem=item
				this.content=item.content
				this.$refs.alertDialog.open()
				const self=this;
				uni.request({
					url: self.request + "/lookMessage",
					data: {
						id:item.id
					},
					method: "POST",
					success: function(res) {
						item.status=1
					}
				})
			}
		},
		onShow() {
			const self = this;
			uni.getStorage({
				key: "user",
				success(r) {
					self.user = r.data
					uni.request({
						url: self.request + "/getMyMessages",
						data: {
							openId: self.user.id
						},
						method: "POST",
						success: function(res) {
							self.messages = res.data
						}
					})
				}
			})
		}
	}
</script>

<style>

</style>