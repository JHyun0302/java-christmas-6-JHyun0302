package christmas.validation;

import static christmas.constant.ValidateMessage.MENU_CANNOT_BE_NULL_OR_EMPTY;
import static christmas.constant.ValidateMessage.MENU_CANNOT_BE_ONLY_DRINK;
import static christmas.constant.ValidateMessage.MENU_CANNOT_ORDER_MORE_THAN_TWENTY;
import static christmas.constant.ValidateMessage.MENU_ERROR_FORM;
import static christmas.constant.ValidateMessage.MENU_IS_DUPLICATE;
import static christmas.constant.ValidateMessage.MENU_IS_NOT_EXIST;
import static christmas.constant.ValidateMessage.MENU_ORDER_MORE_THAN_ONE;

import christmas.constant.Menu;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderValidator {

    public void isNullOrEmpty(String orderMenu) {
        if (orderMenu == null || orderMenu.length() == 0) {
            MENU_CANNOT_BE_NULL_OR_EMPTY.throwException();
        }
    }

    public void checkErrorInput(String orderMenu) {
        char lastChar = orderMenu.charAt(orderMenu.length() - 1);
        if (orderMenu.contains(",,")
                || !Character.isDigit(lastChar)
                && lastChar != ' '
                || !startsWithKorean(orderMenu)) {
            MENU_ERROR_FORM.throwException();
        }
    }

    private boolean startsWithKorean(String s) {
        Pattern pattern = Pattern.compile("^[가-힣]");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    public Map<String, Integer> isErrorForm(String orderMenu) {
        Map<String, Integer> menu = new HashMap<>();
        String[] menuSplit = orderMenu.replace(" ", "").split(",");
        try {
            for (String item : menuSplit) {
                String[] details = item.split("-");
                String menuName = details[0];

                isDuplicate(menu, menuName);
                menu.put(details[0], Integer.parseInt(details[1]));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return menu;
    }

    private void isDuplicate(Map<String, Integer> menu, String menuName) {
        if (menu.containsKey(menuName)) {
            MENU_IS_DUPLICATE.throwException();
        }
    }

    public void isNotExist(Map<String, Integer> menu) {
        for (String menuName : menu.keySet()) {
            if (!Menu.compareFoodName(menuName)) {
                MENU_IS_NOT_EXIST.throwException();
            }
        }
    }

    public void isMoreThanOne(Map<String, Integer> menu) {
        for (Integer menuCount : menu.values()) {
            if (menuCount < 1) {
                MENU_ORDER_MORE_THAN_ONE.throwException();
            }
        }
    }


    public void onlyOrderDrink(Map<String, Integer> menu) {
        boolean allDrinks = true;
        for (String menuName : menu.keySet()) {
            if (!Menu.isOnlyDrink(menuName)) {
                allDrinks = false;
                break;
            }
        }

        if (allDrinks) {
            MENU_CANNOT_BE_ONLY_DRINK.throwException();
        }
    }


    public Map<String, Integer> validateMoreThanTwenty(Map<String, Integer> menu) {
        int sum = 0;
        for (Integer count : menu.values()) {
            sum += count;
        }

        if (sum > 20) {
            MENU_CANNOT_ORDER_MORE_THAN_TWENTY.throwException();
        }

        return menu;
    }
}
