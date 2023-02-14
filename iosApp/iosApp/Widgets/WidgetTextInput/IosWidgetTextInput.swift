//
//  IosWidgetTextInput.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 13.01.2023.
//  Copyright © 2023 IceRock Development. All rights reserved.
//

import UIKit
import shared_ios

class IosWidgetTextInput: WidgetTextInput {
 
    private let root: InpuViewController = {
        let view = InpuViewController()
        return view
    }()
    
    func state(state: String) {
        root.textView.text = state
    }

    func hint(hint: StringDesc) {
        root.textView.placeholder = hint.localized()
    }

    func onChange(onChange: ((String) -> Void)? = nil) {
        let identifier = UIAction.Identifier("TextInputBinding.onTextChanged")
        let action = UIAction(
            identifier: identifier,
            handler: { [unowned self] _ in onChange?(self.root.textView.text ?? "") }
        )

        root.textView.removeAction(identifiedBy: identifier, for: .editingChanged)
        root.textView.addAction(action, for: .editingChanged)
    }
    
    func inputType(inputType: EntityInputType?) {
        if(inputType == EntityInputType.password){
            root.textView.isSecureTextEntry = true
        }
    }

    var layoutModifiers: Redwood_runtimeLayoutModifier = ExposedKt.layoutModifier()
    
    var value: Any { root.view }
}



class InpuViewController: UIViewController, UITextFieldDelegate{
    
    
    let root: MyCardView = {
        let view = MyCardView()
        return view
    }()
    
    let textView: UITextField = {
        let view = FillUITextField()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.borderStyle = .roundedRect
        return view
    }()
    
    override func loadView() {
        self.view = textView
        textView.addConstraint(KeyboardAwareAnimatedConstraint())
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
           self.view.endEditing(true)
           return false
       }
}

class FillUITextField : UITextField{
        override func sizeThatFits(_ size: CGSize) -> CGSize {
            let originalSize = super.sizeThatFits(size)
            return CGSize(width: size.width, height: originalSize.height)
        }
}
