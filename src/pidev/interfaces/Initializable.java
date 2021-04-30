/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.interfaces;

import java.util.List;

/**
 *
 * @author Meriem
 */

    public interface Initializable<class_name> {
    public void add(class_name c);
    public void delete(class_name c);
    public void update(class_name c);
    public List<class_name> read();
    
    
}
