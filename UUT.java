class UUT {
    int[] afterFour(int[] mas){
        if (mas.length==1 && mas[0]==4) return new int[]{};
        for (int i = mas.length-1; i >=0 ; i--) {
            if(mas[i]==4){
                int[] newMas = new int[mas.length-i-1];
                System.arraycopy(mas, i+1, newMas, 0, newMas.length);
                return newMas;
            }
        }
        throw new RuntimeException();
    }

    boolean checkOneFour(int[] mas){
        for (int ma : mas)
            if (ma == 1 || ma == 4) return true;
        return false;
    }
}
