package cn.catguild.anime.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiyan
 * @date 2024/7/25 14:38
 */
@AllArgsConstructor
@Getter
public enum AreaEnum {


	CHINESE_MAINLAND(1, "中国大陆"),

	JAPAN(2, "日本"),

	USA(3, "美国"),

	BRITAIN(4, "英国"),

	CANADA(5, "加拿大"),

	HONG_KONG_CHINA(6, "中国香港"),

	TAIWAN_CHINA(7, "中国台湾"),

	THE_REPUBLIC_OF_KOREA(8, "韩国"),

	FRANCE(9, "法国"),

	THAILAND(10, "泰国"),

	SINGAPORE(12, "新加坡"),

	SPAIN(13, "西班牙"),

	RUSSIA(14, "俄罗斯"),

	GERMANY(15, "德国"),

	OTHER(16, "其他"),

	DENMARK(17, "丹麦"),

	UKRAINE(18, "乌克兰"),

	ISRAEL(19, "以色列"),

	IRAN(20, "伊朗"),

	CROATIA(22, "克罗地亚"),

	ICELAND(24, "冰岛"),

	HUNGARY(24, "匈牙利"),

	SOUTH_AFRICA(25, "南非"),

	INDONESIA(26, "印尼"),

	INDIA(27, "印度"),

	RUKIYE(30, "土耳其"),

	MEXICO(31, "墨西哥"),

	VENEZUELA(32, "委内瑞拉"),

	BRAZIL(33, "巴西"),

	GREECE(34, "希腊"),

	ITALY(35, "意大利"),

	NORWAY(36, "挪威"),

	CZECH_REPUBLIC(37, "捷克"),

	NEW_ZEALAND(39, "新西兰"),

	CHILE(40, "智利"),

	BELGIUM(41, "比利时"),

	POLAND(42, "波兰"),

	AUSTRALIA(43, "澳大利亚"),

	IRELAND(44, "爱尔兰"),

	SWEDEN(45, "瑞典"),

	SWITZERLAND(46, "瑞士"),

	FINLAND(47, "芬兰"),

	SOVIET_UNION(48, "苏联"),

	NETHERLANDS(49, "荷兰"),

	ARGENTINA(51, "阿根廷"),

	CUBA(53, "古巴"),

	PHILIPPINES(53, "菲律宾"),

	KAZAKHSTAN(55, "哈萨克斯坦");


	private final int code;
	private final String desc;

	public static AreaEnum parse(Integer code) {
		for (AreaEnum value : AreaEnum.values()) {
			if (value.code == code) {
				return value;
			}
		}
		throw new RuntimeException("地区枚举错误");
	}

}
