<template>
	<view>
		<view class="box-bg">
			<uni-nav-bar shadow title="公告" />
		</view>
		<uni-section title="公告栏" type="line">
			<uni-collapse accordion>
				<uni-collapse-item v-for="(item,index) in tips" :title="item.title" :key="index">
					<view class="content" style="padding: 20rpx;">
						<rich-text :selectable="true" :nodes="getMarkDown(item.content)"></rich-text>
					</view>
				</uni-collapse-item>
			</uni-collapse>
		</uni-section>

	</view>
</template>

<script>
	import MarkdownIt from 'markdown-it';
	export default {
		data() {
			return {
				tips: [

				]
			}
		},
		methods: {
			getMarkDown(markdownText) {
				const md = new MarkdownIt();
				return md.render(markdownText);
			}
		},
		onShow() {
			const self = this
			uni.request({
				url: self.systemRequest + "/getTips",
				method: "POST",
				success(res) {
					self.tips = res.data
				}
			})
		}
	}
</script>

<style>

</style>